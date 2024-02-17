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
ca:      .word   4
        ;COLONNE B
cb:      .word   4
        ;RIGHE A	
ra:      .word   2
        ;RIGHE B
rb:      .word   4
        ;GRANDEZZA IN BIT 
inc:     .word   8
        ;INDICE 1
i:       .word 0
        ;INDICE 2
j:       .word 0
        ;INDICE 3
k:       .word 0
        ;SETS
sets:    .word 1
        ;ONE
one:     .word 1

    .text
start: LW  r1, ca(r0)               ; puntatore colonne a
       LW  r2, ra(r0)               ; puntatore righe a 
       LW  r3, cb(r0)               ; puntatore colonne b
       LW  r4, rb(r0)               ; puntatore righe b
       DADDI   r5, r0, a            ; puntatore a primo elemento a
       DADDI   r6, r0, b            ; puntatore a primo elemento b
       DADDI   r7, r0, c            ; puntatore a primo elemento c
       LW  r8, inc(r0)              ; puntatore a grandezza in bit
       LW  r9, i(r0)                ; puntatore indice i
       LW  r10, j(r0)               ; puntatore indice j
       LW  r11, k(r0)               ; puntatore indice k
       LW  r12, sets(r0)            ; puntatore a set
       LW  r16, one(r0)             ; puntatore a numero 1
       DADDI   r17, r6, 0           ; puntatore a primo elemento b
       DADDI   r18, r7, 0           ; puntatore a primo elemento c
       L.D  f3, p(r0)               ; inizializzazione parziale
validate:  SLT r12, r1, r16         ; controlla se colonne a maggiore di 0
        BNEZ r12, end               ; se < 1 termina
        SLT r12, r2, r16            ; controlla se righe a maggiore di 0
        BNEZ r12, end               ; se < 1 termina    
        SLT r12, r3, r16            ; controlla se colonne b maggiore di 0
        BNEZ r12, end               ; se < 1 termina
        SLT r12, r4, r16            ; controlla se righe b maggiore di 0
        BNEZ r12, end               ; se < 1 termina
        BNE  r1, r4, end            ; controlla se CA = RB
loop_i: LW r10, j(r0)               ; resetta j
loop_j: LW r11, k(r0)               ; resetta k
loop_k: SLT r12, r11, r16           ; controlla se k < 1
    if_ge2:   DMUL r15, r9, r1      ; i * CA
        DMUL r19, r11, r3           ; k * CB
        DADD r15, r15, r11          ; (i * CA) + k
        DADD r19, r19, r10          ; (k * CB) + j
        DMUL r15, r15, r8           ; ((i * CA) + k) * 8
        DMUL r19, r19, r8           ; ((k * CB) + j) * 8
        DADD r19, r17, r19          ; primo elemento b + indicizzazione
        DADDI r5, r15, 0            ; aggiorna puntatore ad elemento a 
        DADDI r6, r19, 0            ; aggiorna puntatore ad elemento b     
        L.D f0, 0(r5)               ; leggi elemento in A
        L.D f1, 0(r6)               ; leggi elemento in B
        MUL.D f0, f0, f1            ; moltiplicare elementi di A e B
        ADD.D f3, f3, f0            ; sommare risultato parziale 
        DADDI r11, r11, 1           ; incrementare k
        BEQZ r12, pointers          ; salta singola ripetizione se k > 1
     if_lt2: DMUL r15, r9, r1       ; i * CA
        DMUL r19, r11, r3           ; k * CB
        DADD r15, r15, r11          ; (i * CA) + k
        DADD r19, r19, r10          ; (k * CB) + j
        DMUL r15, r15, r8           ; ((i * CA) + k) * 8
        DMUL r19, r19, r8           ; ((k * CB) + j) * 8
        DADD r19, r17, r19          ; primo elemento b + indicizzazione
        DADDI r5, r15, 0            ; aggiorna puntatore ad elemento a 
        DADDI r6, r19, 0            ; aggiorna puntatore ad elemento b     
        L.D f0, 0(r5)               ; leggi elemento in A
        L.D f1, 0(r6)               ; leggi elemento in B
        MUL.D f0, f0, f1            ; moltiplicare elementi di A e B
        ADD.D f3, f3, f0            ; sommare risultato parziale 
        DADDI r11, r11, 1           ; incrementare k
    pointers:  BNE r11, r1, loop_k  ; saltare se k non è uguale a colonne_A
        S.D f3, 0(r7)               ; scrivere risultato in C 
        MUL.D f3, f3, f4            ; resetta risultato parziale
        DADDI r7, r7, 8             ; scorri risultati 
        DADDI r10, r10, 1           ; incrementare j
        BNE r10, r3, loop_j         ; saltare se j non è uguale a colonne_B
        DADDI r9, r9, 1             ; incrementare i
        BNE r9, r2, loop_i          ; saltare se i non è uguale a righe_A 
end: HALT
 