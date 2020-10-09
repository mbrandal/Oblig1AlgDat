# Obligatorisk oppgave 2 i Algoritmer og Datastrukturer

Denne oppgaven er en innlevering i Algoritmer og Datastrukturer.

Oppgaven er levert av Martin Johannessen Brandal, s344082, s344082@oslomet.no


# Beskrivelse av oppgaveløsning
* Oppgave 1
Her skal vi lage en konstruktør for en dobbelt lenket liste og jeg bruker en for-løkke med hjelpenoder for å konvertere arrayet. Forsikrer meg også om at verdien som legges inn ikke er null, og legger inn ett og ett element inn i listen, samtidig som jeg hekter nodene på hverandre.

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
I T fjern så sjekker man først om verdien er lik hode eller halen sin verdi, hvis ikke så itererer man gjennom listen og sjekker hver enkel node om dens verdi er lik parameterverdien. I fjern indeks så henter man ut noden før og etter indeksen, og "hopper over" indeksen ved å koble de to sammen. Returnerer også det fjernede elementet sin verdi.

* Oppgave 8
Her fullfører man iterator-klassen ved å kode next som er metoden som itererer gjennom listen. Lager også en konstruktør som peker direkte på noden tilsvarende indeksparameteren ved hjelp av finnNode. I siste metode returnerer vi en instans av iteratorklassen med pekeren som peker på oppgitt indeks gitt at indeksen er gyldig.
