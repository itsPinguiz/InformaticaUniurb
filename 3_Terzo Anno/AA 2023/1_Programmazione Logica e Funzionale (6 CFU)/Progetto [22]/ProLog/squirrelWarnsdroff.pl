/* Programma Prolog per risolvere il problema del giro del cavallo usando 
   l'algoritmo di Warnsdorff-Squirrel con ricorsione in coda. */

main :-
    leggi_dimensione_scacchiera(Dimensione),
    leggi_posizione(Dimensione, InizioX, InizioY),
    write('Attendere la soluzione...'), nl,
    (   risolvi(Dimensione, (InizioX, InizioY), ScacchieraFinale)
    ->  stampa_scacchiera(ScacchieraFinale)
    ;   write('Soluzione non trovata.'), nl
    ).

/* Predicato che legge e valida la dimensione della scacchiera. 
   - Dimensione: dimensione valida della scacchiera. */
leggi_dimensione_scacchiera(Dimensione) :-
    write('Inserisci la dimensione della scacchiera (intero compreso tra 5 e 70): '),
    read(Ingresso),
    (   integer(Ingresso), Ingresso >= 5, Ingresso =< 70 ->
        Dimensione = Ingresso
    ;   (integer(Ingresso) ->
            write('Dimensione non valida. Deve essere compresa tra 5 e 70.')
        ;   write('Dato inserito non valido. Inserisci un numero intero.')
        ), nl, leggi_dimensione_scacchiera(Dimensione)
    ).

/* Predicato chiamato in caso di errore durante la lettura della posizione. */
fallimento :-
    write('Dato inserito non valido o errore di sintassi. Riprova.\n'),
    fail.

/* Predicato che legge e valida la posizione iniziale del cavallo. 
   - N: dimensione della scacchiera.
   - InizioX, InizioY: coordinate valide della posizione iniziale. */
leggi_posizione(N, InizioX, InizioY) :-
    repeat,
    write('Inserisci la posizione di partenza del cavallo (X,Y)'),
    format(' (X e Y interi compresi tra 1 e ~d): ', [N]),  
    catch(read_term(user_input, Ingresso, []), _, fallimento),
    (   analizza_ingresso(Ingresso, N, InizioX, InizioY)
    ->  !
    ;   fallimento).

/* Predicato che analizza il dato in ingresso e verifica che sia 
   nel formato corretto (X,Y). 
   - Ingresso: il dato da analizzare.
   - N: dimensione della scacchiera.
   - X, Y: coordinate valide. */
analizza_ingresso((X,Y), N, X1, Y1) :-
    integer(X), integer(Y), X > 0, X =< N, Y > 0, Y =< N,  
    X1 is X - 1,  % Convertiamo le coordinate da 1-based a 0-based
    Y1 is Y - 1.
analizza_ingresso(_, _, _, _) :-
    fail.

/* Predicato che risolve il problema del giro del cavallo.
   - Dimensione: dimensione della scacchiera.
   - PosizioneIniziale: posizione iniziale del cavallo (InizioX, InizioY).
   - ScacchieraFinale: la scacchiera finale con il percorso del cavallo. */
risolvi(Dimensione, PosizioneIniziale, ScacchieraFinale) :-
    inizializza_scacchiera(Dimensione, Scacchiera),
    algoritmo_warnsdorff(Dimensione, Scacchiera, PosizioneIniziale, 1, ScacchieraFinale).

/* Predicato che implementa l'algoritmo di Warnsdorff-Squirrel con ricorsione in coda.
   - Dimensione: dimensione della scacchiera.
   - Scacchiera: scacchiera corrente.
   - (X, Y): posizione corrente del cavallo.
   - Mossa: numero della mossa corrente.
   - ScacchieraFinale: la scacchiera finale con il percorso completato. */
algoritmo_warnsdorff(Dimensione, Scacchiera, (X, Y), Mossa, ScacchieraFinale) :-
    aggiorna_scacchiera(Scacchiera, (X, Y), Mossa, ScacchieraAggiornata),
    (   Mossa =:= Dimensione * Dimensione  % Caso base: tutte le celle sono state visitate
    ->  ScacchieraFinale = ScacchieraAggiornata
    ;   findall((NX, NY), 
                (mosse_cavallo((X, Y), (NX, NY)), 
                 mossa_valida(Dimensione, ScacchieraAggiornata, (NX, NY))
                ), 
                Mosse),
        ordina_mosse(Dimensione, ScacchieraAggiornata, Mosse, MosseOrdinate),
        member(ProssimaMossa, MosseOrdinate),
        algoritmo_warnsdorff(Dimensione, ScacchieraAggiornata, ProssimaMossa,
                             Mossa + 1, ScacchieraFinale)
    ).

/* Predicato che inizializza la scacchiera con valori di default (-1).
   - N: dimensione della scacchiera (NxN).
   - Scacchiera: la scacchiera vuota inizializzata. */
inizializza_scacchiera(N, Scacchiera) :-
    length(Scacchiera, N),  % Crea una lista di N righe
    maplist(crea_riga(N), Scacchiera).

/* Predicato che crea una riga della scacchiera con valori di default (-1).
   - N: lunghezza della riga.
   - Riga: la riga creata con valori di default (-1). */
crea_riga(N, Riga) :-
    length(Riga, N),  % Crea una riga di lunghezza N
    maplist(=( -1), Riga).  % Inizializza tutte le celle a -1

/* Predicato che controlla se una mossa e' valida (ossia se e' dentro i confini 
   della scacchiera e la cella non e' stata visitata).
   - N: dimensione della scacchiera.
   - Scacchiera: scacchiera corrente.
   - (X, Y): posizione della mossa da controllare. */
mossa_valida(N, Scacchiera, (X, Y)) :-
    X >= 0, X < N, Y >= 0, Y < N,
    nth0(X, Scacchiera, Riga),
    nth0(Y, Riga, -1).

/* Predicato che calcola l'accessibilita' di una posizione 
    (numero di mosse valide possibili).
   - N: dimensione della scacchiera.
   - Scacchiera: scacchiera corrente.
   - (X, Y): posizione corrente.
   - Grado-(X, Y): il grado di accessibilita' e la posizione. */
calcola_accessibilita_con_posizione(N, Scacchiera, (X, Y), Grado-(X, Y)) :-
    findall((NuovoX, NuovoY), 
            (mosse_cavallo((X, Y), (NuovoX, NuovoY)), 
             mossa_valida(N, Scacchiera, (NuovoX, NuovoY))), Mosse),
    length(Mosse, Grado).

/* Predicato che ordina le mosse in base all'accessibilita' (secondo la regola di Warnsdorff).
   - N: dimensione della scacchiera.
   - Scacchiera: scacchiera corrente.
   - Mosse: lista delle mosse possibili.
   - MosseOrdinate: lista delle mosse ordinate per accessibilita'. */
ordina_mosse(N, Scacchiera, Mosse, MosseOrdinate) :-
    maplist(calcola_accessibilita_con_posizione(N, Scacchiera), Mosse, MosseConAccessibilita),
    keysort(MosseConAccessibilita, MosseOrdinateConAccessibilita),
    estrai_valori(MosseOrdinateConAccessibilita, MosseOrdinate).

/* Predicato che estrae i valori da una lista di coppie chiave-valore.
   - Lista: lista di coppie chiave-valore.
   - Valori: lista dei soli valori. */
estrai_valori([], []).
estrai_valori([_-Val|Resto], [Val|ValoriResto]) :-
    estrai_valori(Resto, ValoriResto).

/* Predicato che aggiorna la scacchiera con una nuova mossa.
   - Scacchiera: scacchiera corrente.
   - (X, Y): posizione della nuova mossa.
   - Mossa: numero della mossa corrente.
   - NuovaScacchiera: scacchiera aggiornata. */
aggiorna_scacchiera(Scacchiera, (X, Y), Mossa, NuovaScacchiera) :-
    (X >= 0, Y >= 0 ->  
        nth0(X, Scacchiera, Riga),  
        aggiorna_lista(Riga, Y, Mossa, NuovaRiga),  
        aggiorna_lista(Scacchiera, X, NuovaRiga, NuovaScacchiera)
    ;   throw(error(domain_error(not_less_than_zero, (X, Y)), _))).

/* Predicato che aggiorna una lista con un nuovo elemento in una posizione specifica.
   - Lista: la lista corrente.
   - Indice: posizione dell'elemento da aggiornare.
   - Elem: il nuovo elemento.
   - NuovaLista: lista aggiornata. */
aggiorna_lista([_|Resto], 0, Elem, [Elem|Resto]) :- !.
aggiorna_lista([Testa|Resto], Indice, Elem, [Testa|NuovoResto]) :-
    (Indice > 0 ->  
        NuovoIndice is Indice - 1,
        aggiorna_lista(Resto, NuovoIndice, Elem, NuovoResto)
    ;   throw(error(domain_error(not_less_than_zero, Indice), _))).

/* Predicato che genera le mosse possibili del cavallo a partire da una posizione.
   - (X, Y): posizione corrente del cavallo.
   - (NuovoX, NuovoY): nuova posizione possibile del cavallo. */
mosse_cavallo((X, Y), (NuovoX, NuovoY)) :-
    member((DeltaX, DeltaY), [(2, 1), (1, 2), (-1, 2), (-2, 1),
                              (-2, -1), (-1, -2), (1, -2), (2, -1)]),
    NuovoX is X + DeltaX,
    NuovoY is Y + DeltaY,
    NuovoX >= 0,
    NuovoY >= 0.

/* Predicato che stampa la scacchiera. */
stampa_scacchiera(Scacchiera) :-
    stampa_righe(Scacchiera).

/* Predicato che stampa tutte le righe della scacchiera. */
stampa_righe([]).
stampa_righe([Riga|Resto]) :-
    stampa_riga(Riga),
    nl,
    stampa_righe(Resto).

/* Predicato che stampa la scacchiera con numeri formattati */
stampa_riga([]).
stampa_riga([Elemento|Resto]) :-
    format('%4.0F ', [Elemento]),  
    stampa_riga(Resto).
