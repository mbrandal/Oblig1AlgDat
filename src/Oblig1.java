import java.util.Arrays;
import java.util.NoSuchElementException;

// Martin Johannessen Brandal s344082
public class Oblig1 {
    // Oppgave 1
    // Det blir flest ombyttinger naar tabellen er sortert synkende
    // Det blir faerrest ombyttinger naar tabellen er sortert stigende
    // Det blir som aa telle inversjoner, altså blir gjennomsnittet n(n-1)/4 inversjoner eller ombyttinger
    // De andre maks-metodene vi har sett paa gjoer ingen ombyttinger fordi de ikke endrer tabellen
    // Men siden vaar metode gjoer tre tilordninger og fire tabelloperasjoner etter sammenligningen per inversjon saa er vaar metode vesentlig daarligere hvis tabellen har inversjoner

    public static int maks(int[] a) {
        if (a.length == 0) {
            throw new NoSuchElementException("Ugyldig tabell, den er tom");
        }
        int temp = 0;
        for (int i = 1; i < a.length; ++i) {
            if (a[i - 1] > a[i]) {
                temp = a[i];
                a[i] = a[i - 1];
                a[i - 1] = temp;
            }
        }
        return a[a.length - 1];
    }

    public static int ombyttinger(int[] a) {
        int temp = 0;
        int teller = 0;
        for (int i = 1; i < a.length; ++i) {
            if (a[i - 1] > a[i]) {
                temp = a[i];
                a[i] = a[i - 1];
                a[i - 1] = temp;
                teller++;
            }
        }
        return teller;
    }

    // Oppgave 2
    public static int antallUlikeSortert(int[] a) {
        boolean sortert = true;
        int teller = 1;

        if (a.length == 0) {
            return 0;
        }
        for (int i = 1; i < a.length; ++i) {
            if (a[i - 1] > a[i]) {
                sortert = false;
            }
            if (a[i - 1] != a[i]) {
                teller++;
            }
        }
        if (!sortert) {
            throw new IllegalStateException("Tabellen er ikke sortert stigende!");
        }
        return teller;
    }

    public static int usortertsok(int[] a, int value) {
        for (int i = 0; i < a.length; ++i) {
            if (a[i] == value) {
                return i;
            }
        }
        return -1;
    }

    //Oppgave 3
    public static int antallUlikeUsortert(int[] a) {
        int teller = 0;
        if (a.length == 0) {
            return 0;
        }
        for (int i = 0; i < a.length; ++i) {
            if (usortertsok(a, a[i]) > -1) {
                teller++;
            }
            if (usortertsok(a, a[i]) != i) {
                teller--;
            }
        }
        return teller;
    }

    //Oppgave 4
    public static void delsortering(int[] a) {
        int v = 0;
        int h = a.length - 1;
        int teller = 0;
        while (v < h) {
            while (a[v] % 2 != 0) {
                v++;
                teller++;
                if(v==a.length-1) {
                    teller++;
                    break;
                }
            }
            while (a[h] % 2 == 0 && v < h) {
                h--;
            }
            if (v < h)
                bytt(a, v, h);
        }
        kvikksortering(a,0,teller);
        kvikksortering(a,teller,a.length);
    }
    // Oppgave 5
    public static void rotasjon(char[] a) {
        if(a.length>1) {
        char [] b = Arrays.copyOf(a,a.length);
        a[0] = b[a.length-1];
            for (int i = 1; i < a.length; ++i) {
                a[i] = b[i - 1];
            }
        }
    }

    //Oppgave 7
    public static String flett(String s, String t) {
        int n = s.length();
        int m = t.length();
        int k = n - m;
        String ut = "";
        if (k <= 0) {
            for (int i = 0; i < n; ++i) {
                ut += s.charAt(i);
                ut += t.charAt(i);
            }
            if (k < 0) {
                for (int i = -(k); i > 0; --i) {
                    ut += t.charAt(m - i);
                }
            }
        }
        if (k > 0) {
            for (int i = 0; i < m; ++i) {
                ut += s.charAt(i);
                ut += t.charAt(i);
            }
            for (int i = k; i > 0; --i) {
                ut += s.charAt(n - i);
            }
        }
        return ut;
    }

    public static String flett(String ... s) {
        String ut = "";
        int j = 0;
            for (int i = 0; i < s.length; ++i) {
                if (j < s[i].length()) {
                    for (; j < s[i].length(); j = j) {
                        ut += s[i].charAt(j);
                        break;
                    }
                }
                if(j == ut.length()+1) {
                    break;
                }
                if(i == s.length-1) {
                    i = -1;
                    j++;
                }
            }
        return ut;
    }

    // Hjelpemetoder
    private static int parter0(int[] a, int v, int h, int skilleverdi)
    {
        while (true)
        {
            while (v <= h && a[v] < skilleverdi) v++;
            while (v <= h && a[h] >= skilleverdi) h--;

            if (v < h) bytt(a,v++,h--);
            else  return v;
        }
    }
    private static int sParter0(int[] a, int v, int h, int indeks)
    {
        bytt(a, indeks, h);
        int pos = parter0(a, v, h - 1, a[h]);
        bytt(a, pos, h);
        return pos;
    }
    private static void kvikksortering0(int[] a, int v, int h)
    {
        if (v >= h) return;
        int k = sParter0(a, v, h, (v + h)/2);
        kvikksortering0(a, v, k - 1);
        kvikksortering0(a, k + 1, h);
    }
    public static void kvikksortering(int[] a, int fra, int til)
    {
        kvikksortering0(a, fra, til - 1);
    }

    public static void bytt(int[] a, int i, int j)
    {
        int temp = a[i]; a[i] = a[j]; a[j] = temp;
    }

}
