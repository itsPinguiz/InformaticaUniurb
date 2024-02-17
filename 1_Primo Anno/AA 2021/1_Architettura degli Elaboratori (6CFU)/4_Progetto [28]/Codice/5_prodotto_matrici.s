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
    	MUL.D   f9, f1, f2      ; a[i]*b[i]
        MUL.D   f10, f3, f4     ; a[i]*b[i]
        MUL.D   f11, f5, f6     ; a[i]*b[i]
        MUL.D   f12, f7, f8     ; a[i]*b[i]
    	ADD.D   f9, f9, f10     ; p = p + a[i]*b[i] (risultato parziale)
    	ADD.D   f11, f11, f12   ; p = p + a[i]*b[i] (risultato parziale)
    	ADD.D   f0, f9, f11     ; p = p + a[i]*b[i] (risultato parziale)
    	S.D     f0, 0(r4)       ; salva il risultato 
    	MUL.D   f0, f0, f31     ; resetta il risultato parziale

    	L.D     f2, 8(r3)       ; leggi b[i]
    	L.D     f4, 40(r3)      ; leggi b[i]
    	L.D     f6, 72(r3)      ; leggi b[i]
    	L.D     f8, 104(r3)     ; leggi b[i]
   		MUL.D   f9, f1, f2      ; a[i]*b[i]
        MUL.D   f10, f3, f4     ; a[i]*b[i]
        MUL.D   f11, f5, f6     ; a[i]*b[i]
        MUL.D   f12, f7, f8     ; a[i]*b[i]
    	ADD.D   f9, f9, f10     ; p = p + a[i]*b[i] (risultato parziale)
    	ADD.D   f11, f11, f12   ; p = p + a[i]*b[i] (risultato parziale)
    	ADD.D   f0, f9, f11     ; p = p + a[i]*b[i] (risultato parziale)
    	S.D     f0, 8(r4)       ; salva il risultato 
    	MUL.D   f0, f0, f31     ; resetta il risultato parziale

		L.D     f2, 16(r3)      ; leggi b[i]
    	L.D     f4, 48(r3)      ; leggi b[i]
    	L.D     f6, 80(r3)      ; leggi b[i]
    	L.D     f8, 112(r3)     ; leggi b[i]
   		MUL.D   f9, f1, f2      ; a[i]*b[i]
        MUL.D   f10, f3, f4     ; a[i]*b[i]
        MUL.D   f11, f5, f6     ; a[i]*b[i]
        MUL.D   f12, f7, f8     ; a[i]*b[i]
    	ADD.D   f9, f9, f10     ; p = p + a[i]*b[i] (risultato parziale)
    	ADD.D   f11, f11, f12   ; p = p + a[i]*b[i] (risultato parziale)
    	ADD.D   f0, f9, f11     ; p = p + a[i]*b[i] (risultato parziale)
    	S.D     f0, 16(r4)      ; salva il risultato 
    	MUL.D   f0, f0, f31     ; resetta il risultato parziale

		L.D     f2, 24(r3)      ; leggi b[i]
    	L.D     f4, 56(r3)      ; leggi b[i]
    	L.D     f6, 88(r3)      ; leggi b[i]
    	L.D     f8, 120(r3)     ; leggi b[i]
   		MUL.D   f9, f1, f2      ; a[i]*b[i]
        MUL.D   f10, f3, f4     ; a[i]*b[i]
        MUL.D   f11, f5, f6     ; a[i]*b[i]
        MUL.D   f12, f7, f8     ; a[i]*b[i]
    	ADD.D   f9, f9, f10     ; p = p + a[i]*b[i] (risultato parziale)
    	ADD.D   f11, f11, f12   ; p = p + a[i]*b[i] (risultato parziale)
    	ADD.D   f0, f9, f11     ; p = p + a[i]*b[i] (risultato parziale)
    	S.D     f0, 24(r4)      ; salva il risultato 
    	MUL.D   f0, f0, f31     ; resetta il risultato parziale
		
		DADDI   r4, r4, 32	; scorri registro risultati
rows:	DADDI r2, r2, 32        ; riporta puntatore a alla seconda riga
		DADDI r6, r6, -1		; decrementa contatore righe a 
		BNEZ  r6, loop			; ripeti se contatore right a diverso da 0
end:	HALT
