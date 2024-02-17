	.data
        ;MATRICE A
a:      .double 4.3,2.1,3.3,5.2,4.8,9.3,2.3,0.5
        ;MATRICE B
b:      .double 2.3,1.1,4.3,6.2,5.3,5.5,3.5,7.3,4.9,2.3,4.3,2.1,2.3,0.5,1.8,1.1
        ;MATRICE C
c:      .double 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0   
        ;PARZIALE
p:      .double 0
        ;RIGHE A	
o:      .word   2

    .text
start:  DADDI   r2, r0, a       ; puntatore a primo elemento a
    	DADDI   r3, r0, b       ; puntatore a primo elemento b
    	DADDI   r4, r0, c       ; puntatore a primo elemento c
    	LW  r6, o(r0)           ; puntatore righe a
    	L.D f0, p(r0)           ; inizializzazione parziale
    	
loop:   L.D     f1, 0(r2)       ; leggi a[i]
    	L.D     f2, 0(r3)       ; leggi b[i]
        L.D     f3, 8(r2)       ; leggi a[i]
    	L.D     f4, 32(r3)      ; leggi b[i]
        L.D     f5, 16(r2)      ; leggi a[i]
    	L.D     f6, 64(r3)      ; leggi b[i]
        L.D     f7, 24(r2)      ; leggi a[i]
    	L.D     f8, 96(r3)      ; leggi b[i]

        L.D     f9, 8(r3)       ; leggi b[i]
    	L.D     f10, 40(r3)     ; leggi b[i]
    	L.D     f11, 72(r3)     ; leggi b[i]
    	L.D     f12, 104(r3)    ; leggi b[i]

        L.D     f13, 16(r3)     ; leggi b[i]
    	L.D     f14, 48(r3)     ; leggi b[i]
    	L.D     f15, 80(r3)     ; leggi b[i]
    	L.D     f16, 112(r3)    ; leggi b[i]

        L.D     f17, 24(r3)     ; leggi b[i]
    	L.D     f18, 56(r3)     ; leggi b[i]
    	L.D     f19, 88(r3)     ; leggi b[i]
    	L.D     f20, 120(r3)    ; leggi b[i]

    	MUL.D   f21, f1, f2     ; a[i]*b[i]
        MUL.D   f22, f3, f4     ; a[i]*b[i]
        MUL.D   f23, f5, f6     ; a[i]*b[i]
        MUL.D   f24, f7, f8     ; a[i]*b[i]
        MUL.D   f25, f1, f9     ; a[i]*b[i]
        MUL.D   f26, f3, f10    ; a[i]*b[i]
        MUL.D   f27, f5, f11    ; a[i]*b[i]
        MUL.D   f28, f7, f12    ; a[i]*b[i]
        MUL.D   f2, f1, f13     ; a[i]*b[i]
        MUL.D   f4, f3, f14     ; a[i]*b[i]
        MUL.D   f6, f5, f15     ; a[i]*b[i]
        MUL.D   f8, f7, f16     ; a[i]*b[i]
        MUL.D   f9, f1, f17     ; a[i]*b[i]
        MUL.D   f10, f3, f18    ; a[i]*b[i]
        MUL.D   f11, f5, f19    ; a[i]*b[i]
        MUL.D   f12, f7, f20    ; a[i]*b[i]

        ADD.D   f25, f25, f26   ; p = p + a[i]*b[i] (risultato parziale)
    	ADD.D   f27, f27, f28   ; p = p + a[i]*b[i] (risultato parziale)
    	ADD.D   f21, f21, f22   ; p = p + a[i]*b[i] (risultato parziale)
    	ADD.D   f23, f23, f24   ; p = p + a[i]*b[i] (risultato parziale)
        ADD.D   f9, f9, f10     ; p = p + a[i]*b[i] (risultato parziale)
        ADD.D   f2, f2, f4      ; p = p + a[i]*b[i] (risultato parziale)
        ADD.D   f6, f6, f8      ; p = p + a[i]*b[i] (risultato parziale)
        ADD.D   f11, f11, f12   ; p = p + a[i]*b[i] (risultato parziale)
    	
    	ADD.D   f21, f21, f23   ; risulato finale 1
    	ADD.D   f25, f25, f27   ; risultato finale 2
    	ADD.D   f2, f2, f6      ; risultato finale 3 
    	ADD.D   f9, f9, f11     ; risultato finale 4

		DADDI r6, r6, -1		; decrementa contatore righe a 
        DADDI   r4, r4, 32	    ; scorri registro risultati
    	DADDI   r2, r2, 32      ; riporta puntatore a alla seconda riga

        S.D     f21, 0(r4)      ; salva il risultato 
    	S.D     f25, 8(r4)      ; salva il risultato 
    	S.D     f2, 16(r4)      ; salva il risultato 
    	S.D     f9, 24(r4)      ; salva il risultato 
		BNEZ    r6, loop		; ripeti se contatore right a diverso da 0
end:	HALT
