prog: main.o lib.o makefile
	gcc -lm -O -o prog main.o lib.o
main.o: main.c
	gcc -Wall -ansi -O -c main.c
lib.o: lib.c lib.h
	gcc -Wall -ansi -O -c lib.c
pulisci:
	rm -f *.o
pulisci_tutto:
	rm -f *.*
