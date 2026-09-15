import { execFileSync } from 'node:child_process';

const base = process.env.GITHUB_BASE_REF;
if (!base) throw new Error('GITHUB_BASE_REF is required.');

const addedFiles = execFileSync('git', ['diff', '--name-only', '--diff-filter=A', `origin/${base}...HEAD`], { encoding: 'utf8' }).split('\n').filter(Boolean);
const allowedRootFiles = new Set(['README.md', 'CONTRIBUTING.md', 'LICENSE', '.gitignore', '.gitattributes']);
const coursePath = /^[123]_(?:Primo|Secondo|Terzo) Anno\/AA \d{4}\/\d+(?:-\d+)?_.+ \(\d+ CFU\)\/.+$/;
const errors = [];
const maxFileSize = 50 * 1024 * 1024;

for (const file of addedFiles) {
  const size = Number(execFileSync('git', ['cat-file', '-s', 'HEAD:' + file], { encoding: 'utf8' }));
  if (size > maxFileSize) errors.push(file + ' — il file supera il limite di 50 MB; riducine le dimensioni o suddividilo.');
  if (file.startsWith('.github/') || allowedRootFiles.has(file)) continue;
  const existingCourse = file.split('/').slice(0, 3).join('/');
  try {
    // Existing folders are grandfathered, including historic names such as "6CFU".
    execFileSync('git', ['cat-file', '-e', `origin/${base}:${existingCourse}`], { stdio: 'ignore' });
    continue;
  } catch {
    // New course folders must follow the current convention.
  }
  if (!coursePath.test(file)) errors.push(`${file} — usa N_Anno/AA YYYY/N_Nome corso (N CFU)/`);
}

if (errors.length) {
  console.error('I nuovi materiali devono rispettare questa struttura:\n  1_Primo Anno/AA 2026/0_Nome del Corso (6 CFU)/\n');
  console.error(errors.map((error) => `- ${error}`).join('\n'));
  process.exit(1);
}
console.log('Struttura dei nuovi materiali valida.');