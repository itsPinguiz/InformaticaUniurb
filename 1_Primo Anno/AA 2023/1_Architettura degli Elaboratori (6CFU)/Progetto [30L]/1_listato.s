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
  SUB.D f3, f3, f1   ; decadimento del valore della cella
  C.LE.D f3, f2      ; f3 <= 0.3
  BC1F skip_0        ; se falso salta
  MOV.D f3, f0       ; la cella corrente muore
skip_0:
  S.D f3, 0(r2)      ; salvataggio risultato
  DADDI r2, r2, 8    ; passo all'elemento successivo
  DADDI r1, r1, -1   ; decrementa contatore
BNEZ r1, loopM

;************
; PASSAGGIO_2
;************
LW r1, cont(r0)     ; carico il valore iniziale del contatore
DADDI r10, r0, 0    ; imposto il valore iniziale della posizione
DADDI r2, r0, vett  ; puntatore al primo elemento dell'array
DADDI r8, r0, 2     ; Carica il valore 2 in r8
loopE:
  DADDI r3, r0, 0    ; inizializzazione contatore vicini vivi -> alive_neighbors
  DADDI r4, r10, -1  ; posizione vicino di sinistra -> j
  DADDI r5, r10, 1   ; posizione vicino di destra -> w

  SLT r6, r4, r0       ; r6 = 1 se r4 < 0 altrimenti r6 = 0 
  BNEZ r6, skip_1     ; se non è uguale a zero fa il salto. --> j >= 0
    DADDI r7, r2, -8  ; puntatore di sinistra
    L.D f4, 0(r7)     ; leggi valore del vicino di sinistra -> grid[j]
    C.EQ.D f4, f0     ; grid[j] == 0
    BC1T skip_1       ; se vera fa il salto
      DADDI r3, r3, 1   ; alive_neighbors++

skip_1: 
  SLT r6, r5, r9      ; r6 = 1 se r5 < 16 altrimenti r6 = 0 
  BEQZ r6, skip_2     ; se è uguale a zero fa il salto. --> w <= 15
    DADDI r7, r2, 8   ; puntatore di destra
    L.D f4, 0(r7)     ; leggi valore del vicino di destra -> grid[w]
    C.EQ.D f4, f0     ; grid[w] == 0
    BC1T skip_2       ; se vera fa il salto
      DADDI r3, r3, 1   ; alive_neighbors++

skip_2:
  L.D f3, 0(r2)     ; leggi valore cella corrente -> grid[i]
  C.EQ.D f3, f0     ; grid[i] == 0
  BC1T skip_3       ; se vera fa il salto
  BNEZ r3, skip_4   ; se non è uguale a zero fa il salto --> alive_neighbors != 0
    S.D f0, 0(r2)   ; setta la cella corrente come morta
    J skip_4        ; salto alla fine del ciclo

skip_3:
  BNE r3, r8, skip_4   ; Se r3 != r8, salta --> alive_neighbors != 2
    S.D f5, 0(r2)      ; setta la cella corrente come rinata

skip_4:
  DADDI r2, r2, 8      ; passo all'elemento successivo
  DADDI r10, r10, 1    ; successiva posizione
  DADDI r1, r1, -1     ; decrementa contatore

BNEZ r1, loopE

HALT ; Termina il programma