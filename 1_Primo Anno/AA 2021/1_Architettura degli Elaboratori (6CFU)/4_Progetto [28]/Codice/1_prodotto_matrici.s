	.data
        ;MATRICE A
a:      .double 4.3,2.1,3.3,5.2,4.8,9.3,2.3,0.5
        ;MATRICE B
b:      .double 2.3,1.1,4.3,6.2,5.3,5.5,3.5,7.3,4.9,2.3,4.3,2.1,2.3,0.5,1.8,1.1
        ;MATRICE C
c:      .double 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0   
        ;PARZIALE
p:      .double 0
        ;COLONNE A 
n:      .word   4
        ;COLONNE B
m:      .word   4
        ;RIGHE A	
o:      .word   2

    .text
start:  LW  r1, n(r0)           ; puntatore colonne a 
        DADDI   r2, r0, a       ; puntatore a primo elemento a
        DADDI   r3, r0, b       ; puntatore a primo elemento b
        DADDI   r4, r0, c       ; puntatore a primo elemento c
        LW  r5, m(r0)           ; puntatore colonne b
        LW  r6, o(r0)           ; puntatore righe a
        L.D f0, p(r0)           ; inizializzazione parziale
loop:   L.D f1, 0(r2)           ; leggi a[i]
        L.D     f2, 0(r3)       ; leggi b[i]
        MUL.D   f1, f1, f2      ; a[i]*b[i]
        ADD.D   f0, f0, f1      ; p = p + a[i]*b[i] (risultato parziale)
        DADDI   r2, r2, 8       ; scorri elemento a
        DADDI   r3, r3, 32      ; scorri elemento b
        DADDI   r1, r1, -1      ; decrementa contatore colonne a
        BNEZ    r1, loop        ; ripeti se contatore colonne a diverso da 0
        S.D     f0, 0(r4)       ; salva il risultato 
        MUL.D   f0, f0, f3      ; resetta il risultato parziale
pointers:	DADDI   r4, r4, 8		; scorri registro risultati
	DADDI   r3, r3, -120	; riporta puntatore b all'inizio + 1
	DADDI   r2, r2, -32	; riporta puntatore a all'inizio
	DADDI   r1, r1, 4	; resetta contatore colonne a
	DADDI   r5, r5, -1	; decrementa contatore colonne b
	BNEZ 	r5, loop	; ripeti se contatore colonne b diverso da 0
rows:	DADDI r2, r2, 32        ; riporta puntatore a alla seconda riga
	DADDI r3, r3, -32	; riporta puntatore b all'inizio
	DADDI   r5, r5, 4	; resetta numero di colonne b
	DADDI r6, r6, -1	; decrementa contatore righe a 
	BNEZ  r6, loop		; ripeti se contatore right a diverso da 0
end:	HALT
