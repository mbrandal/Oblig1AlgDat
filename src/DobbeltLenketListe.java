import java.util.Iterator;

/////////// DobbeltLenketListe ////////////////////////////////////
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class DobbeltLenketListe<T> implements Liste<T> {
    private static final class Node<T>   // en indre nodeklasse
    {
        // instansvariabler
        private T verdi;
        private Node<T> forrige, neste;

        private Node(T verdi, Node<T> forrige, Node<T> neste)  // konstruktør
        {
            this.verdi = verdi;
            this.forrige = forrige;
            this.neste = neste;
        }

        protected Node(T verdi)  // konstruktør
        {
            this(verdi, null, null);
        }

    } // Node

    // instansvariabler
    private Node<T> hode;          // peker til den første i listen
    private Node<T> hale;          // peker til den siste i listen
    private int antall;            // antall noder i listen
    private int endringer;   // antall endringer i listen

    // hjelpemetode
    private Node<T> finnNode(int indeks) {
        if (indeks < (antall / 2) + 1 || indeks == 0) { // Starter helt fremst fordi indeksen er i første halvdel
            Node<T> p = hode;
            for (int i = 0; i < indeks; i++) p = p.neste;
            return p;
        } else {                     // Starter bakerst fordi indeksen er i andre halvdel
            Node<T> p = hale;
            for (int i = antall - 1; i > indeks; i--) p = p.forrige;
            return p;
        }
    }

    // konstruktør
    public DobbeltLenketListe() {
        hode = hale = null;
        antall = 0;
        endringer = 0;
    }

    // konstruktør
    public DobbeltLenketListe(T[] a) {
        Objects.requireNonNull(a, "Tabellen a er null!");             // ingen verdier - tom liste - kaster "automatisk" en NullPointerException
        if (a.length > 0) {
            Node<T> p = hode;
            for (int i = a.length - 1; i >= 0; i--)  // resten av verdiene
                if (a[i] != null) {
                    if (antall > 0) { // For å forsikre seg om at hode og hale bare settes èn gang, hvis antall er over 0 så har hale og hode fått verdier
                        hode = new Node<T>(a[i], hode.forrige, p);
                        antall++;
                        if (i == 0) hode.forrige = null;
                        p = hode;
                        if (antall == 2)
                            hale.forrige = p; // For å vite at man er nest bakerst slik at hale.forrige henviser til riktig node
                        hode.neste.forrige = hode;

                    } else {
                        hode = hale = new Node<T>(a[i], null, null);  // den siste noden
                        antall++;
                        p = hode;
                    }
                }
        }
    }

    // subliste
    public Liste<T> subliste(int fra, int til) {
        fratilKontroll(antall(), fra, til);
        DobbeltLenketListe<T> utliste = new DobbeltLenketListe<>();
        int temp = antall; // For å få finnNode til å funke så gjør jeg om antallet midlertidig
        utliste.antall = 0;
        antall = til;
        Node<T> p = finnNode(fra);
        for (int i = fra; i < til; i++) {
            utliste.leggInn(p.verdi);
            p = p.neste;
            utliste.antall++;
        }
        antall = temp;
        return utliste;
    }


    private static void fratilKontroll(int antall, int fra, int til) {
        if (fra < 0)                                  // fra er negativ
            throw new IndexOutOfBoundsException
                    ("fra(" + fra + ") er negativ!");

        if (til > antall)                          // til er utenfor tabellen
            throw new IndexOutOfBoundsException
                    ("til(" + til + ") > antall(" + antall + ")");

        if (fra > til)                                // fra er større enn til
            throw new IllegalArgumentException
                    ("fra(" + fra + ") > til(" + til + ") - illegalt intervall!");
    }

    @Override
    public int antall() {
        return antall;
    }

    @Override
    public boolean tom() {
        return antall == 0;
    }

    @Override
    public boolean leggInn(T verdi) {
        Objects.requireNonNull(verdi, "Ikke tillatt med null-verdier!");
        if (antall == 0) hode = hale = new Node<T>(verdi, null, null);  // tom liste
        else hale = hale.neste = new Node<T>(verdi, hale, null); // Denne legges bakerst

        antall++;
        endringer++;
        return true;
    }

    @Override
    public void leggInn(int indeks, T verdi) {
        Objects.requireNonNull(verdi, "Ikke tillatt med null-verdier!");

        indeksKontroll(indeks, true);   // Det er lov at indeks = antall

        if (antall == 0 || indeks == antall) leggInn(verdi);  // Hvis listen er tom eller verdi skal legges bakerst
        else if (indeks == 0)                // Hvis verdi skal legges foran
        {
            Node<T> p = hode;
            hode = new Node<T>(verdi, null, hode);
            p.forrige = hode;

            endringer++;      // En innlegging er en endring
            antall++;          // Antallet elementer i listen har økt
        } else {
            Node<T> q = finnNode(indeks - 1);
            Node<T> p = finnNode(indeks);
            q.neste = new Node<T>(verdi, q, p);
            p.forrige = q.neste;

            endringer++;      // En innlegging er en endring
            antall++;          // Antallet elementer i listen har økt

        }


    }

    @Override
    public boolean inneholder(T verdi) {
        return indeksTil(verdi) >= 0;
    }

    @Override
    public T hent(int indeks) {
        indeksKontroll(indeks, false);  // Indeks = antall går ikke
        return finnNode(indeks).verdi;
    }

    @Override
    public int indeksTil(T verdi) {
        if (verdi == null) return -1;
        for (int i = 0; i < antall; i++) {
            if (hent(i).equals(verdi)) return i;
        }
        return -1;
    }

    @Override
    public T oppdater(int indeks, T nyverdi) {
        Objects.requireNonNull(nyverdi, "Ikke lov med null-verdier!");

        indeksKontroll(indeks, false);  // Indeks = antall går ikke

        Node<T> p = finnNode(indeks);
        T gammelVerdi = p.verdi;

        p.verdi = nyverdi;
        endringer++;    // En endring i listen

        return gammelVerdi;
    }

    @Override
    public boolean fjern(T verdi) {
        if (verdi == null) return false;

        Node<T> q = hode;

        if (verdi.equals(q.verdi)) {
            if (q.neste != null) {
                hode = q.neste;
                hode.forrige = null;
            } else {
                hode = null;
                hale = null;
            }
            antall--;
            endringer++;
            return true;
        }

        q = hale;
        if (verdi.equals(q.verdi)) {
            hale = q.forrige;       // oppdaterer hale
            hale.neste = null;
            antall--;
            endringer++;
            return true;
        }

        q = hode.neste;
        for (; q != null; q = q.neste)                     // Prøver å finne verdien
        {
            if (q.verdi.equals(verdi)) {       // verdien funnet
                q.forrige.neste = q.neste;
                q.neste.forrige = q.forrige;
                antall--;
                endringer++;
                return true;
            }
        }
        return false;
    }

    @Override
    public T fjern(int indeks) {
        indeksKontroll(indeks, false);  // Indeks = antall går ikke

        T temp = null;

        if (indeks > 0 && indeks < antall - 1) {  // En node midt i skal fjernes
            Node<T> q = finnNode(indeks - 1);  // Noden før den som skal fjernes
            temp = q.neste.verdi;                // Verdien her skal returneres
            Node<T> r = q.neste.neste;           // Noden etter den som skal fjernes

            q.neste = r;
            r.forrige = q;
        } else if (indeks == 0) {    // Den første noden skal fjernes
            temp = hode.verdi;
            hode = hode.neste;
            if (antall != 1) hode.forrige = null;
            else {
                hode = hale = null;
            }
        } else if (indeks == antall - 1) {  // Den siste noden skal fjernes
            temp = hale.verdi;
            hale = hale.forrige;
            hale.neste = null;
        }

        antall--;
        if (antall == 1) hode = hale; // Hvis det er èn verdi i listen skal hode og hale peke på samme
        if (antall == 0) hale = hode = null; // Hvis det er en tom liste skal hode og hale ha null-pekere
        endringer++;
        return temp;
    }

    @Override
    public void nullstill() {
        this.hode = null;
        this.hale = null;
        antall = 0;
        endringer++;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();

        s.append('[');

        if (!tom()) {
            Node<T> p = hode;
            s.append(p.verdi);

            p = p.neste;

            while (p != null)  // tar med resten hvis det er noe mer
            {
                s.append(',').append(' ').append(p.verdi);
                p = p.neste;
            }
        }

        s.append(']');

        return s.toString();
    }

    public String omvendtString() {
        StringBuilder s = new StringBuilder();

        s.append('[');

        if (!tom()) {
            Node<T> p = hale;
            s.append(p.verdi);

            p = p.forrige;

            while (p != null)  // tar med resten hvis det er noe mer
            {
                s.append(',').append(' ').append(p.verdi);
                p = p.forrige;
            }
        }

        s.append(']');

        return s.toString();
    }

    public static <T> void sorter(Liste<T> liste, Comparator<? super T> c) {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    @Override
    public Iterator<T> iterator() {
        return new DobbeltLenketListeIterator();
    }

    public Iterator<T> iterator(int indeks) {
        indeksKontroll(indeks, false);     // False betyr indeks = antall ikke er lovlig
        return new DobbeltLenketListeIterator(indeks);
    }

    private class DobbeltLenketListeIterator implements Iterator<T> {
        private Node<T> denne;
        private boolean fjernOK;
        private int iteratorendringer;

        private DobbeltLenketListeIterator() {
            denne = hode;     // denne starter på den første i listen
            fjernOK = false;  // blir sann når next() kalles
            iteratorendringer = endringer;  // teller endringer
        }

        private DobbeltLenketListeIterator(int indeks) {
            denne = finnNode(indeks);   // denne starter på indeks
            fjernOK = false;  // blir sann når next() kalles
            iteratorendringer = endringer;  // teller endringer
        }

        @Override
        public boolean hasNext() {
            return denne != null;  // denne koden skal ikke endres!
        }

        @Override
        public T next() {
            if (endringer != iteratorendringer)
                throw new ConcurrentModificationException("Listen er endret!");

            if (!hasNext()) throw new
                    NoSuchElementException("Tomt eller ingen verdier igjen!");

            fjernOK = true;            // nå kan remove() kalles

            T denneVerdi = denne.verdi;    // tar vare på verdien i p
            denne = denne.neste;               // flytter p til den neste noden

            return denneVerdi;         // returnerer verdien
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Ikke laget ennå!");
        }

    } // DobbeltLenketListeIterator
    // DobbeltLenketListe
}
