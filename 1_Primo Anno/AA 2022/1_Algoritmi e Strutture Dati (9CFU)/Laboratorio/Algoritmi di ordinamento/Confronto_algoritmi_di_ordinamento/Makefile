#Makefile algoritmi
main : algo.o main.o Makefile
		gcc -ansi -Wall -O algo.o main.o -o algoritmi

algo.o : algo.c algo.h Makefile
		gcc -ansi -Wall -O -c algo.c

main.o : main.c Makefile
		gcc -ansi -Wall -O -c main.c

pulisci :
		rm -f *.o
		
pulisci_tutto :
		rm -f algoritmi *.o
