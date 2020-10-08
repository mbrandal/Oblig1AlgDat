# Obligatorisk oppgave 2 i Algoritmer og Datastrukturer

Denne oppgaven er en innlevering i Algoritmer og Datastrukturer.

Oppgaven er levert av Martin Johannessen Brandal, s344082, s344082@oslomet.no


# Beskrivelse av oppgaveløsning
* Oppgave 1
Her skal vi lage en konstruktør for en dobbelt lenket liste og jeg bruker en for-løkke med hjelpenoder for å konvertere arrayet. Forsikrer meg også om at verdien som legges inn ikke er null.

* Oppgave 2
Her lages det en toString metode ved hjelp av StringBuilder. Når den vanlige toString() var riktig, var det bare å bytte fra hode til hale på den omvendte toString-metoden.
Deretter lages det en leggInn metode som skal legge verdien bakerst i listen. Her forsikrer jeg meg også om at verdien ikke er null, og tar høyde for at listen kan være tom.

* Oppgave 3
I finnNode henter vi noden tilknyttet indeksen som er inn-parameter. finnNode sjekker om indeksen er langt bak eller foran i listen, og starter søket fremst eller bakerst basert på det.
Subliste-metoden sjekker om intervallet er riktig og returnerer en ny DobbeltLenketListe ved hjelp av leggInn-metoden og en for-løkke.

* Oppgave 4
Her returneres indeksen til verdi ved hjelp av metoden hent() og en for-løkke som sjekker om noden sin verdi i indeks i er lik verdi, isåfall returneres posisjonen (i).
Inneholder-metoden bruker indeksTil for å sjekke om den finner verdien eller ikke, og returnerer en boolsk verdi basert på det.

* Oppgave 5
Her legges verdien inn på gitt indeks. Sjekker at indeks er gyldig og at null-verdier stoppes. 
Ved hjelp av finnNode så finner man nodene som skal være før og etter verdien som skal inn, og oppdaterer pekerne slik at de blir riktige etter at den nye noden er lagt inn.

* Oppgave 6

* Oppgave 8
