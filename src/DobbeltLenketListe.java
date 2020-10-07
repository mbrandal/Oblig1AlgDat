import java.util.Iterator;

/////////// DobbeltLenketListe ////////////////////////////////////
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class DobbeltLenketListe<T> implements Liste<T>
{
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
        }
        else {                     // Starter bakerst fordi indeksen er i andre halvdel
            Node<T> p = hale;
            for (int i = antall-1; i > indeks; i--) p = p.forrige;
            return p;
        }
    }

    // konstruktør
    public DobbeltLenketListe()
    {
        hode = hale = null;
        antall = 0;
        endringer = 0;
    }

    // konstruktør
    public DobbeltLenketListe(T[] a)
    {
            this();
            Objects.requireNonNull(a,"Tabellen a er null!");             // ingen verdier - tom liste - kaster "automatisk" en NullPointerException
            if(a.length > 0) {
                Node<T> p = hode;
                for (int i = a.length - 1; i >= 0; i--)  // resten av verdiene
                {
                    if (a[i] != null) {
                        if(antall > 0) { // For å forsikre seg om at hode og hale bare settes èn gang, hvis antall er over 0 så har hale og hode fått verdier
                            hode = new Node<T>(a[i], hode.forrige, p);
                            antall++;
                            if (i == 0) hode.forrige = null;
                            p = hode;
                            if (antall == 2) hale.forrige = p; // For å vite at man er nest bakerst slik at hale.forrige henviser til riktig node
                        }
                        else {
                            hode = hale = new Node<T>(a[i], null, null);  // den siste noden
                            antall++;
                            p = hode;
                        }

                    }
                }
            }

        }

    // subliste
    public Liste<T> subliste(int fra, int til)
    {
        fratilKontroll(antall(),fra,til);
        DobbeltLenketListe<T> utliste = new DobbeltLenketListe<T>();
        int temp = antall; // For å få finnNode til å funke så gjør jeg om antallet midlertidig
        utliste.antall = 0;
        antall = til;
        Node<T> p = finnNode(fra);
        for(int i = fra; i < til; i++) {
           utliste.leggInn(p.verdi);
           p = p.neste;
           utliste.antall++;
        }
        antall = temp;
        return utliste;
    }


    private static void fratilKontroll(int antall, int fra, int til)
    {
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
    public int antall()
    {
        return antall;
    }

    @Override
    public boolean tom()
    {
       return antall == 0;
    }

    @Override
    public boolean leggInn(T verdi)
    {
        Objects.requireNonNull(verdi, "Ikke tillatt med null-verdier!");
        if (antall == 0) hode = hale = new Node<T>(verdi, null,null);  // tom liste
        else hale = hale.neste = new Node<T>(verdi,hale,null); // Denne legges bakerst

        antall++;
        endringer++;
        return true;
    }

    @Override
    public void leggInn(int indeks, T verdi)
    {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    @Override
    public boolean inneholder(T verdi)
    {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    @Override
    public T hent(int indeks)
    {
        indeksKontroll(indeks, false);  // Indeks = antall går ikke
        return finnNode(indeks).verdi;
    }

    @Override
    public int indeksTil(T verdi)
    {

    }

    @Override
    public T oppdater(int indeks, T nyverdi)
    {
        Objects.requireNonNull(nyverdi, "Ikke lov med null-verdier!");

        indeksKontroll(indeks, false);  // Indeks = antall går ikke

        Node<T> p = finnNode(indeks);
        T gammelVerdi = p.verdi;

        p.verdi = nyverdi;
        endringer++;    // En endring i listen

        return gammelVerdi;
    }

    @Override
    public boolean fjern(T verdi)
    {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    @Override
    public T fjern(int indeks)
    {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    @Override
    public void nullstill()
    {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    @Override
    public String toString()
    {
        StringBuilder s = new StringBuilder();

        s.append('[');

        if (!tom())
        {
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

    public String omvendtString()
    {
        StringBuilder s = new StringBuilder();

        s.append('[');

        if (!tom())
        {
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

    public static <T> void sorter(Liste<T> liste, Comparator<? super T> c)
    {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    @Override
    public Iterator<T> iterator()
    {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    public Iterator<T> iterator(int indeks)
    {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    private class DobbeltLenketListeIterator implements Iterator<T>
    {
        private Node<T> denne;
        private boolean fjernOK;
        private int iteratorendringer;

        private DobbeltLenketListeIterator()
        {
            denne = hode;     // denne starter på den første i listen
            fjernOK = false;  // blir sann når next() kalles
            iteratorendringer = endringer;  // teller endringer
        }

        private DobbeltLenketListeIterator(int indeks)
        {
            throw new UnsupportedOperationException("Ikke laget ennå!");
        }

        @Override
        public boolean hasNext()
        {
            return denne != null;  // denne koden skal ikke endres!
        }

        @Override
        public T next()
        {
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
        public void remove()
        {
            throw new UnsupportedOperationException("Ikke laget ennå!");
        }

    } // DobbeltLenketListeIterator
    // DobbeltLenketListe

    public static void main(String [] args) {
        /*String[] s1 = {}, s2 = {"A"}, s3 = {null,"A",null,"B",null};
        DobbeltLenketListe<String> l1 = new DobbeltLenketListe<>(s1);
        DobbeltLenketListe<String> l2 = new DobbeltLenketListe<>(s2);
        DobbeltLenketListe<String> l3 = new DobbeltLenketListe<>(s3);
        System.out.println(l1.toString() + " " + l2.toString()
                + " " + l3.toString() + " " + l1.omvendtString() + " "
                + l2.omvendtString() + " " + l3.omvendtString());
// Utskrift: [] [A] [A, B] [] [A] [B, A]
         */
        Character[] c = {'A','B','C','D','E','F','G','H','I','J',};
        DobbeltLenketListe<Character> liste = new DobbeltLenketListe<>(c);
        System.out.println(liste.subliste(3,8)); // [D, E, F, G, H]
        System.out.println(liste.subliste(5,5)); // []
        System.out.println(liste.subliste(8,liste.antall())); // [I, J]
// System.out.println(liste.subliste(0,11)); // skal kaste unntak


    }
}
