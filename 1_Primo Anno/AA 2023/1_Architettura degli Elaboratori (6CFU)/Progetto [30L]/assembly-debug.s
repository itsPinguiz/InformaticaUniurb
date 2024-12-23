.data

sub:    .double 0.1   ; valore decadimento                                                        
soglia: .double 0.3   ; valore di soglia
renew:  .double 1.0   ; valore rinascita
cont:   .word 16      ; contatore / numero elementi                                                                  

; valori della griglia
vett:  .double 1.3, 0.7, 0.5, 0.2, 0.6, 0.9, 0.3, 0.0, 0.8, 1.2, 0.1, 0.7, 0.3, 0.2, 0.5, 0.1

.code

LW r1, cont(r0)     ; carico il valore iniziale del contatore
LW r9, cont(r0)     ; carico il valore degli elementi
L.D f1, sub(r0)     ; carico il valore per la sottrazione
L.D f2, soglia(r0)  ; carico il valore di soglia
L.D f5, renew(r0)   ; carico il valore di rinascita
DADDI r2, r0, vett  ; puntatore al primo elemento dell'array

;************
; PASSAGGIO_1
;************
loopM:
  L.D f3, 0(r2)      ; leggi valore cella
  L.D f6, 8(r2)      ; leggi valore cella
  DADDI r1, r1, -1   ; decrementa contatore
  SUB.D f3, f3, f1   ; decadimento del valore della cella
  DADDI r2, r2, 8    ; passo all'elemento successivo
  DADDI r1, r1, -1   ; decrementa contatore
  
  SUB.D f6, f6, f1   ; decadimento del valore della cella
  DADDI r2, r2, 8    ; passo all'elemento successivo
  
  C.LE.D f3, f2      ; f3 <= 0.3
  BC1F skip0         ; se falso salta
  MOV.D f3, f0       ; la cella corrente muore
skip0:
  C.LE.D f6, f2      ; f6 <= 0.3
  BC1F skip1         ; se falso salta
  MOV.D f6, f0       ; la cella corrente muore
skip1:
  S.D f6, -8(r2)     ; salvataggio risultato
  S.D f3, -16(r2)    ; salvataggio risultato

BNEZ r1, loopM

;************
; PASSAGGIO_2
;************
LW r1, cont(r0)     ; carico il valore iniziale del contatore
DADDI r10, r0, 0    ; imposto il valore iniziale della posizione
DADDI r2, r0, vett  ; puntatore al primo elemento dell'array
DADDI r8, r0, 2     ; Carica il valore 2 in r8
loopE:
  DADDI r3, r0, 0   ; inizializzazione contatore vicini vivi -> alive_neighbors
  
  DADDI r4, r10, -1  ; posizione vicino di sinistra -> j
  DADDI r5, r10, 1   ; posizione vicino di destra -> w
  SLT r6, r4, r0     ; r6 = 1 se r4 < 0 altrimenti r6 = 0
  SLT r15, r5, r9    ; r15 = 1 se r5 < 16 altrimenti r15 = 0
  
  DADDI r12, r10, -2  ; posizione vicino di sinistra -> j
  DADDI r13, r10, 2   ; posizione vicino di destra -> w
  SLT r11, r12, r0    ; r11 = 1 se r12 < 0 altrimenti r11 = 0
  SLT r14, r13, r9    ; r14 = 1 se r13 < 16 altrimenti r14 = 0
  
  BNEZ r6, skip2       ; se non è uguale a zero fa il salto. --> j >= 0
    L.D f4, -8(r2)     ; leggi valore del vicino di sinistra -> grid[j]
    C.EQ.D f4, f0      ; grid[j] == 0
    BC1T skip2         ; se vera fa il salto
      DADDI r3, r3, 1  ; alive_neighbors++

skip2: 
  BEQZ r15, skip3       ; se è uguale a zero fa il salto. --> w <= 15
    L.D f4, 8(r2)       ; leggi valore del vicino di destra -> grid[w]
    C.EQ.D f4, f0       ; grid[w] == 0
    BC1T skip3          ; se vera fa il salto
      DADDI r3, r3, 1   ; alive_neighbors++

skip3:
  L.D f3, 0(r2)     ; leggi valore cella corrente -> grid[i]
  DADDI r1, r1, -1  ; decrementa contatore
  C.EQ.D f3, f0     ; grid[i] == 0
  BC1T skip4        ; se vera fa il salto
  BNEZ r3, skip5    ; se non è uguale a zero fa il salto --> alive_neighbors != 0
    S.D f0, 0(r2)   ; setta la cella corrente come morta
    J skip5         ; salto alla fine del ciclo

skip4:
  BNE r3, r8, skip5    ; Se r3 != r8, salta --> alive_neighbors != 2
    S.D f5, 0(r2)      ; setta la cella corrente come rinata

skip5:
  DADDI r2, r2, 8   ; passo all'elemento successivo
  DADDI r3, r0, 0   ; inizializzazione contatore vicini vivi -> alive_neighbors
  
  BNEZ r11, skip6      ; se non è uguale a zero fa il salto. --> j >= 0
    L.D f4, -8(r2)     ; leggi valore del vicino di sinistra -> grid[j]
    C.EQ.D f4, f0      ; grid[j] == 0
    BC1T skip6         ; se vera fa il salto
      DADDI r3, r3, 1  ; alive_neighbors++

skip6:
  BEQZ r14, skip7       ; se è uguale a zero fa il salto. --> w <= 15
    L.D f4, 8(r2)       ; leggi valore del vicino di destra -> grid[w]
    C.EQ.D f4, f0       ; grid[w] == 0
    BC1T skip7          ; se vera fa il salto
      DADDI r3, r3, 1   ; alive_neighbors++

skip7:
  L.D f3, 0(r2)     ; leggi valore cella corrente -> grid[i]
  DADDI r1, r1, -1  ; decrementa contatore
  C.EQ.D f3, f0     ; grid[i] == 0
  BC1T skip8        ; se vera fa il salto
  BNEZ r3, skip9    ; se non è uguale a zero fa il salto --> alive_neighbors != 0
    S.D f0, 0(r2)   ; setta la cella corrente come morta
    J skip9         ; salto alla fine del ciclo

skip8:
  BNE r3, r8, skip9   ; Se r3 != r8, salta --> alive_neighbors != 2
    S.D f5, 0(r2)     ; setta la cella corrente come rinata

skip9:
  DADDI r2, r2, 8     ; passo all'elemento successivo
  DADDI r10, r10, 2   ; avanzo posizione

BNEZ r1, loopE

NOP
NOP
NOP
NOP
NOP
NOP
NOP
NOP
NOP

;*****
;DEBUG
;*****
DADDI r2, r0, vett  ; puntatore al primo elemento dell'array
L.D f10, 0(r2)    ; leggi valore cella
DADDI r2, r2, 8  ; passo all'elemento successivo
L.D f11, 0(r2)    ; leggi valore cella
DADDI r2, r2, 8  ; passo all'elemento successivo
L.D f12, 0(r2)    ; leggi valore cella
DADDI r2, r2, 8  ; passo all'elemento successivo
L.D f13, 0(r2)    ; leggi valore cella
DADDI r2, r2, 8  ; passo all'elemento successivo
L.D f14, 0(r2)    ; leggi valore cella
DADDI r2, r2, 8  ; passo all'elemento successivo
L.D f15, 0(r2)    ; leggi valore cella
DADDI r2, r2, 8  ; passo all'elemento successivo
L.D f16, 0(r2)    ; leggi valore cella
DADDI r2, r2, 8  ; passo all'elemento successivo
L.D f17, 0(r2)    ; leggi valore cella
DADDI r2, r2, 8  ; passo all'elemento successivo
L.D f18, 0(r2)    ; leggi valore cella
DADDI r2, r2, 8  ; passo all'elemento successivo
L.D f19, 0(r2)    ; leggi valore cella
DADDI r2, r2, 8  ; passo all'elemento successivo
L.D f20, 0(r2)    ; leggi valore cella
DADDI r2, r2, 8  ; passo all'elemento successivo
L.D f21, 0(r2)    ; leggi valore cella
DADDI r2, r2, 8  ; passo all'elemento successivo
L.D f22, 0(r2)    ; leggi valore cella
DADDI r2, r2, 8  ; passo all'elemento successivo
L.D f23, 0(r2)    ; leggi valore cella
DADDI r2, r2, 8  ; passo all'elemento successivo
L.D f24, 0(r2)    ; leggi valore cella
DADDI r2, r2, 8  ; passo all'elemento successivo
L.D f25, 0(r2)    ; leggi valore cella

HALT ; Termina il programma