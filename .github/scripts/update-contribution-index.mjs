import { execFileSync } from 'node:child_process';
import { readFileSync, writeFileSync } from 'node:fs';

const page = 'CONTRIBUTING.md';
const files = execFileSync('git', ['ls-files'], { encoding: 'utf8' }).split('\n').filter(Boolean);
const courses = new Map();
const normaliseCourse = (course) => course
  .replace(/^\d+(?:-\d+)?_/, '')
  .replace(/\(\s*(\d+)\s*CFU\s*\)/i, '($1 CFU)')
  .replace(/\s+/g, ' ')
  .trim();
const canonicalCourses = new Map([
  ['prog a oggetti (9 cfu)', 'Programmazione e Modellazione ad Oggetti (9 CFU)'],
  ['reti logiche (9 cfu)', 'Reti Logiche (6 CFU)']
]);
const canonicaliseCourse = (course) => canonicalCourses.get(course.replace(/_/g, ' ').toLocaleLowerCase('it')) ?? course;

for (const file of files) {
  const match = file.match(/^([123])_([^/]+)\/AA (\d{4})\/([^/]+)\/(.+)$/);
  if (!match) continue;
  const [, year, label, academicYear, folder, item] = match;
  const course = canonicaliseCourse(normaliseCourse(folder));
  const key = `${year}|${label}|${course.toLocaleLowerCase('it')}`;
  if (!courses.has(key)) courses.set(key, { year, label, course, folders: new Map(), types: new Set() });
  const entry = courses.get(key);
  if (!entry.folders.has(academicYear)) entry.folders.set(academicYear, new Set());
  entry.folders.get(academicYear).add(folder);
  const name = item.toLowerCase();
  entry.types.add(name.includes('progetto') ? 'Progetto' : name.includes('esam') ? 'Esami' : name.includes('appunt') || name.includes('dispens') || name.includes('riassunt') ? 'Appunti' : 'Altro materiale');
}

const labels = { 1: 'Primo Anno', 2: 'Secondo Anno', 3: 'Terzo Anno' };
let index = '| Anno | Corso | Anni accademici | Materiale |\n| --- | --- | --- | --- |\n';
for (const year of ['1', '2', '3']) {
  const entries = [...courses.values()].filter(x => x.year === year).sort((a, b) => a.course.localeCompare(b.course, 'it'));
  for (const entry of entries) {
    const years = [...entry.folders.entries()].sort(([a], [b]) => a.localeCompare(b)).flatMap(([academicYear, folders]) => [...folders].sort().map(folder => `[${academicYear}](https://github.com/itsPinguiz/InformaticaUniurb/tree/main/${entry.year}_${encodeURIComponent(entry.label)}/AA%20${academicYear}/${encodeURIComponent(folder)})`)).join(', ');
    index += `| ${labels[year]} | ${entry.course} | ${years} | ${[...entry.types].sort().join(', ')} |\n`;
  }
}

let text = readFileSync(page, 'utf8').replace(/<!-- CONTENTS-INDEX:START -->[\s\S]*?<!-- CONTENTS-INDEX:END -->/, `<!-- CONTENTS-INDEX:START -->\n${index.trim()}\n<!-- CONTENTS-INDEX:END -->`);
const login = process.env.CONTRIBUTOR_LOGIN;
if (login && !text.includes(`[@${login}](https://github.com/${login})`)) text = text.replace('<!-- CONTRIBUTORS:END -->', `| ${login} | [@${login}](https://github.com/${login}) |\n<!-- CONTRIBUTORS:END -->`);
writeFileSync(page, text);