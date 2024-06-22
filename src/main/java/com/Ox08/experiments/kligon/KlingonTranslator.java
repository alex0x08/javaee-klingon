package com.Ox08.experiments.kligon;
import java.util.HashMap;
import java.util.Map;
/**
 * This code is based on Javascript implmentation, found here:
 * <a href="https://dadap.github.io/pIqaD-tools/universal-transliterator/">...</a>
 * using the standard Okrandian transliteration to pIqaD
 *
 * @author <a href="mailto:alex3.145@gmail.com">Alex Chernyshev</a>
 * @since 1.0
 */
public class KlingonTranslator {
    static final Map<Character, String> x2p = new HashMap<>(), x2tlh = new HashMap<>();
    static {
        /*
         * // Transliteration tables: don't bother mapping identity
         * relationships for // tlhIngan-Hol <-> xifan-hol
         */
        x2tlh.put('c', "ch");
        x2tlh.put('d', "D");
        x2tlh.put('f', "ng");
        x2tlh.put('g', "gh");
        x2tlh.put('h', "H");
        x2tlh.put('i', "I");
        x2tlh.put('k', "q");
        x2tlh.put('q', "Q");
        x2tlh.put('s', "S");
        x2tlh.put('x', "tlh");
        x2tlh.put('z', "'");
        x2p.put('`', "\uf8ff");
        x2p.put('a', "\uf8d0");
        x2p.put('b', "\uf8d1");
        x2p.put('c', "\uf8d2");
        x2p.put('d', "\uf8d3");
        x2p.put('e', "\uf8d4");
        x2p.put('f', "\uf8dc");
        x2p.put('g', "\uf8d5");
        x2p.put('h', "\uf8d6");
        x2p.put('i', "\uf8d7");
        x2p.put('j', "\uf8d8");
        x2p.put('k', "\uf8df");
        x2p.put('l', "\uf8d9");
        x2p.put(',', "\uf8fd");
        x2p.put('m', "\uf8da");
        x2p.put('n', "\uf8db");
        x2p.put('.', "\uf8fe");
        x2p.put('o', "\uf8dd");
        x2p.put('p', "\uf8de");
        x2p.put('0', "\uf8f0");
        x2p.put('q', "\uf8e0");
        x2p.put('1', "\uf8f1");
        x2p.put('r', "\uf8e1");
        x2p.put('2', "\uf8f2");
        x2p.put('s', "\uf8e2");
        x2p.put('3', "\uf8f3");
        x2p.put('t', "\uf8e3");
        x2p.put('4', "\uf8f4");
        x2p.put('u', "\uf8e5");
        x2p.put('5', "\uf8f5");
        x2p.put('v', "\uf8e6");
        x2p.put('6', "\uf8f6");
        x2p.put('w', "\uf8e7");
        x2p.put('7', "\uf8f7");
        x2p.put('x', "\uf8e4");
        x2p.put('8', "\uf8f8");
        x2p.put('y', "\uf8e8");
        x2p.put('9', "\uf8f9");
        x2p.put('z', "\uf8e9");
    }
    public static String transliterate(String source) {
        String out = source.replaceAll("[‘’]+", "'");
        // Replace {gh} with non-standard {G}, to prevent {ngh} from being 
        // matched as {ng}, *{h}.
        out = out.replaceAll("gh", "G");
        // Handle {ng} and {tlh} first, to prevent {n}, {t}, and {l} from
        // spuriously being pulled out of these letters.
        out = out.replaceAll("ng", "f")
                .replaceAll("tlh", "x");
        for (Map.Entry<Character, String> e : x2tlh.entrySet()) 
            out = out.replaceAll(e.getValue(), String.valueOf(e.getKey()));
        
        // Now that all {ng}s have been processed, it's safe for {gh} to be 'g'
        out = out.replaceAll("G", "g");
        final StringBuilder out2 = new StringBuilder();
        for (int i = 0; i < out.length(); i++) {
            char before = i > 0 ? out.charAt(i - 1) : (char) -1, 
                    c = out.charAt(i),
                    after = i < out.length() - 1 ? out.charAt(i + 1) : (char) -1;
            // require to solve MessageFormat's substitution patterns
            if (before == '{' && after == '}') {
                out2.append(c);
                continue;
            }
            //Process char
            out2.append(x2p.getOrDefault(c, c + ""));
        }
        return out2.toString();
    }
}
