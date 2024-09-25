{- Programma Haskell per risolvere il problema del giro del cavallo usando 
   l'algoritmo di Warnsdorff-Squirrel. -}

import Data.List (sortBy)
{- La libreria Data.List e' necessaria per utilizzare la funzione sortBy 
   che ordina una lista in base a un criterio specifico. -}

import Data.Ord (comparing)
{- La libreria Data.Ord e' necessaria per utilizzare la funzione comparing 
   che crea un criterio di ordinamento basato su una funzione di proiezione. -}

import Data.Maybe (listToMaybe)
{- La libreria Data.Maybe e' utilizzata per la funzione listToMaybe che converte 
   una lista in un Maybe, prendendo il primo elemento della lista se esiste. -}

import Data.IORef (IORef, newIORef, readIORef, writeIORef)
{- La libreria Data.IORef e' utilizzata per creare e manipolare riferimenti 
   mutabili (IORef) necessari per gestire lo stato mutabile nel programma. -}

import Text.Read (readMaybe)
{- La libreria Text.Read e' necessaria per utilizzare la funzione readMaybe 
   che tenta di leggere un valore da una stringa e restituisce Maybe. -}

-- Coordinate del cavallo
type Posizione = (Int, Int)

-- Scacchiera che contiene le mosse che compie il cavallo
type Scacchiera = [[Int]]

-- Movimenti del cavallo
mosseCavallo :: [Posizione]
mosseCavallo = [(2, 1), (1, 2), (-1, 2), (-2, 1), 
                (-2, -1), (-1, -2), (1, -2), (2, -1)]

main :: IO ()
main = do
    dimensione <- leggiDimensioneScacchiera
    posizioneIniziale <- leggiPosizione dimensione
    putStrLn "Attendere la soluzione..."
    risultato <- risolviGiroCavallo dimensione posizioneIniziale
    case risultato of
        Just soluzione -> stampaScacchiera soluzione
        Nothing        -> putStrLn "Soluzione non trovata."

{- Funzione che legge la dimensione della scacchiera da tastiera 
   e verifica la validita'.
   - restituisce un intero che rappresenta la dimensione della scacchiera. -}
leggiDimensioneScacchiera :: IO Int
leggiDimensioneScacchiera = do
    putStrLn "Inserisci la dimensione della scacchiera (intero compreso tra 5 e 70):"
    datoIngresso <- getLine
    case readMaybe datoIngresso of
        Just dimensione
            | dimensione < 5 -> do
                putStrLn "Non esiste una soluzione per scacchiere "
                putStrLn "di dimensioni inferiori a 5."
                leggiDimensioneScacchiera
            | dimensione > 70 -> do
                putStrLn "Con questo algoritmo non e' possibile risolvere il problema"
                putStrLn "per scacchiere di dimensioni superiori a 70."
                leggiDimensioneScacchiera
            | otherwise -> return dimensione
        Nothing -> do
            putStrLn "Dimensione non valida. Inserisci un numero intero."
            leggiDimensioneScacchiera

-- Funzione che legge una posizione da tastiera (da 1 a N) e la converte 
-- in coordinate valide per l'algoritmo (da 0 a N-1).
leggiPosizione :: Int -> IO (Int, Int)
leggiPosizione dimensione = do
    putStrLn "Inserisci la posizione di partenza del cavaliere in formato (X,Y)"
    putStrLn $ "(X e Y interi compresi tra 1 e " ++ show dimensione ++ "):"
    datoIngresso <- getLine
    -- Usa reads per cercare di interpretare l'input come una tupla
    case reads datoIngresso :: [((Int, Int), String)] of
        [((x, y), "")] 
            | x > 0 && x <= dimensione && y > 0 && y <= dimensione -> 
                return (x - 1, y - 1)  -- Converte le coordinate da 1-based a 0-based
        _ -> erroreInput dimensione

-- Funzione di errore che ristampa il messaggio di input non valido
erroreInput :: Int -> IO (Int, Int)
erroreInput dimensione = do
    putStrLn "Dato non valido. Inserisci di nuovo (esempio: (3,3)):"
    leggiPosizione dimensione

{- Funzione che inizializza una scacchiera NxN a -1.
   - il primo argomento e' la dimensione della scacchiera. -}
inizializzaScacchiera :: Int -> Scacchiera
inizializzaScacchiera dimensione = replicate dimensione (replicate dimensione (-1))

{- Funzione che verifica se una posizione e' valida.
   - il primo argomento e' la dimensione della scacchiera.
   - il secondo argomento e' la scacchiera.
   - il terzo argomento e' la posizione da verificare. -}
mossaValida :: Int -> Scacchiera -> Posizione -> Bool
mossaValida dimensione scacchiera (x, y) =
    x >= 0 && x < dimensione &&
    y >= 0 && y < dimensione &&
    (scacchiera !! x !! y) == -1

{- Funzione che calcola il numero di mosse valide successive da una data posizione.
   - il primo argomento e' la dimensione della scacchiera.
   - il secondo argomento e' la scacchiera.
   - il terzo argomento e' la posizione da cui calcolare l'accessibilita'. -}
calcolaAccessibilita :: Int -> Scacchiera -> Posizione -> Int
calcolaAccessibilita dimensione scacchiera (x, y) =
    length $ filter (mossaValida dimensione scacchiera) 
                    [(x + dx, y + dy) | (dx, dy) <- mosseCavallo]

{- Funzione che ordina le mosse in base all'accessibilita'.
   - il primo argomento e' la dimensione della scacchiera.
   - il secondo argomento e' la scacchiera.
   - il terzo argomento e' la lista delle posizioni da ordinare. -}
ordinaMosse :: Int -> Scacchiera -> [Posizione] -> [(Int, Posizione)]
ordinaMosse dimensione scacchiera mosse =
    sortBy (comparing fst) $
    map (\pos -> (calcolaAccessibilita dimensione scacchiera pos, pos)) mosse

{- Funzione che aggiorna la scacchiera con la nuova mossa.
   - il primo argomento e' la scacchiera.
   - il secondo argomento e' la posizione della mossa.
   - il terzo argomento e' il numero della mossa corrente. -}
aggiornaScacchiera :: Scacchiera -> Posizione -> Int -> Scacchiera
aggiornaScacchiera scacchiera (x, y) mossa =
    take x scacchiera ++
    [take y (scacchiera !! x) ++ [mossa] ++ drop (y + 1) (scacchiera !! x)] ++
    drop (x + 1) scacchiera

{- Funzione che risolve il problema del giro del cavallo usando l'algoritmo 
   di Warnsdorff-Squirrel.
   - il primo argomento e' la dimensione della scacchiera.
   - il secondo argomento e' la posizione di partenza del cavaliere. -}
risolviGiroCavallo :: Int -> Posizione -> IO (Maybe Scacchiera)
risolviGiroCavallo dimensione partenza = do
    riferimentoScacchiera <- newIORef (dimensione * dimensione, 
                                       inizializzaScacchiera dimensione)
    algoritmoWarnsdorffSquirrel dimensione (inizializzaScacchiera dimensione) 
                                partenza 1 riferimentoScacchiera

{- Funzione che implementa l'algoritmo di Warnsdorff-Squirrel per risolvere il giro 
   del cavallo.
   - il primo argomento e' la dimensione della scacchiera.
   - il secondo argomento e' la scacchiera corrente.
   - il terzo argomento e' la posizione corrente del cavallo.
   - il quarto argomento e' il numero della mossa corrente.
   - il quinto argomento e' un riferimento IORef contenente lo stato delle 
     caselle mancanti e la scacchiera. -}
algoritmoWarnsdorffSquirrel :: Int -> Scacchiera -> Posizione 
                            -> Int -> IORef (Int, Scacchiera) -> IO (Maybe Scacchiera)
algoritmoWarnsdorffSquirrel dimensione scacchiera posizione mossa 
                            riferimentoScacchiera = do
    let scacchieraAggiornata = aggiornaScacchiera scacchiera posizione mossa
    let caselleMancanti = dimensione * dimensione - mossa
    writeIORef riferimentoScacchiera (caselleMancanti, scacchieraAggiornata)
    if mossa == dimensione * dimensione
        then return (Just scacchieraAggiornata)
        else do
            let prossimeMosse = filter (mossaValida dimensione scacchieraAggiornata) 
                                [(fst posizione + dx, snd posizione + dy) 
                                | (dx, dy) <- mosseCavallo]
            if null prossimeMosse
                then return Nothing
                else do
                    let mosseOrdinate = ordinaMosse dimensione scacchieraAggiornata 
                                        prossimeMosse
                    tentaMossa mosseOrdinate scacchieraAggiornata
  where
    tentaMossa [] _ = return Nothing
    tentaMossa ((_, prossimaPosizione):resto) scacchieraCorrente = do
        risultato <- algoritmoWarnsdorffSquirrel dimensione scacchieraCorrente 
                                                 prossimaPosizione (mossa + 1) 
                                                 riferimentoScacchiera
        case risultato of
            Just soluzione -> return (Just soluzione)
            Nothing        -> tentaMossa resto scacchieraCorrente

{- Funzione che stampa la scacchiera.
   - l'unico argomento e' la scacchiera da stampare. -}
stampaScacchiera :: Scacchiera -> IO ()
stampaScacchiera scacchiera = mapM_ (putStrLn . unwords . map (pad . show)) scacchiera
  where pad s = replicate (3 - length s) ' ' ++ s
