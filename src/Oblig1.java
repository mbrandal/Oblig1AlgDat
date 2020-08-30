import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Random;

// Martin Johannessen Brandal s344082
public class Oblig1 {


    // Det blir flest ombyttinger når tabellen er sortert synkende
    // Det blir færrest ombyttinger når tabellen er sortert stigende
    // Vi får n-H_n som gjennomsnitt
    // De andre maks-metodene vi har sett på gjør ingen ombyttinger fordi de ikke endrer tabellen
    // Men siden vår metode har 3 tilordninger, som a[i-1] = a[i], der de andre metodene bare har 2 tilordninger så er vår metode dårligere
    public static int maks(int[] a) {
        if (a.length == 0) {
            throw new NoSuchElementException("Ugyldig tabell, den er tom");
        }
        int temp = 0;
        for(int i = 1; i < a.length; ++i) {
        if(a[i-1] > a[i]) {
            temp = a[i];
            a[i] = a[i-1];
            a[i-1] = temp;
        }
        }
        return a[a.length-1];
    }

    public static int ombyttinger(int[] a) {
        int temp = 0;
        int teller = 0;
        for(int i = 1; i < a.length; ++i) {
            if(a[i-1] > a[i]) {
                temp = a[i];
                a[i] = a[i-1];
                a[i-1] = temp;
                teller ++;
            }
        }
        return teller;
    }

    public static int antallUlikeSortert(int [] a) {
        boolean sortert = true;
        int teller = 0; //

        if(a.length == 0) {
            return 0;
        }
        for(int i = 1; i < a.length; ++i) {
            if(a[i-1] > a[i]) {
                sortert = false;
            }
            if(a[i-1] != a[i]) {
                teller ++;
            }
            if(i == a.length-1) {
                teller ++;
            }
        }
        if(!sortert) {
            throw new IllegalStateException("Tabellen er ikke sortert stigende!");
        }
       return teller;
    }

    public static int antallUlikeUsortert(int [] a) {
        int temp = 0;
        int teller = 0;
        int minus = 0;

        if(a.length == 0) {
            return 0;
        }
        for(int i = 0; i < a.length; ++i) {
            teller = 0;
            for(int j = 0; j < a.length; ++j) {
                temp = a[i];
                if(temp == a[j]) {
                    teller ++;
                }
            }
            if(teller > 1) {
                teller = (int) java.lang.Math.sqrt(teller);
                teller --;
                System.out.println(teller);
                minus += teller;
            }
        }
        return a.length-minus;
    }

    public static int[] randPerm(int n)  // en effektiv versjon
    {
        Random r = new Random();         // en randomgenerator
        int[] a = new int[n];            // en tabell med plass til n tall

        Arrays.setAll(a, i -> i + 1);    // legger inn tallene 1, 2, . , n

        for (int k = n - 1; k > 0; k--)  // løkke som går n - 1 ganger
        {
            int i = r.nextInt(k+1);        // en tilfeldig tall fra 0 til k
            bytt(a,k,i);                   // bytter om
        }

        return a;                        // permutasjonen returneres
    }

    public static void bytt(int[] a, int i, int j)
    {
        int temp = a[i]; a[i] = a[j]; a[j] = temp;
    }

    public static void main (String [] args) {
        int [] b = {3,2,9,1,14,12,19,4,11,11,11,11,11};
        int [] c = {1,2,3,3,3};
        System.out.println(maks(b));
        System.out.println(antallUlikeUsortert(c));
    }

}
