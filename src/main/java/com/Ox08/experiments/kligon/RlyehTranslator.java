package com.Ox08.experiments.kligon;
import java.util.HashMap;
import java.util.Map;
/**
 * Based on
 * https://github.com/JLDevOps/Rlyehian/tree/master/rlyehian
 * @since 1.1
 * @author alex0x08
 */
public class RlyehTranslator {
    private final static Map<String, String> compendium = new HashMap<>();
    static {
        // 1 -suffix, 0 - word
        compendium.put("speak", "1_'ai");
        compendium.put("call", "0_'ai");
        compendium.put("body", "0_'bthnk");
        compendium.put("essence", "0_'bthnk");
        compendium.put("mother", "0_fhalma");
        compendium.put("sign", "0_athg");
        compendium.put("contract", "0_athg");
        compendium.put("agree", "0_athg");
        compendium.put("go", "0_bug");
        compendium.put("cross over", "0_ch'");
        compendium.put("travel", "0_ch'");
        compendium.put("brotherhood", "0_chtenff");
        compendium.put("society", "0_chtenff");
        compendium.put("pit", "0_ebumma");
        compendium.put("answers", "0_ee");
        compendium.put("cohesion", "0_ehye");
        compendium.put("integrity", "0_ehye");
        compendium.put("later", "0_ep");
        compendium.put("then", "0_ep");
        compendium.put("wait", "0_fhtagn");
        compendium.put("sleep", "0_fhtagn");
        compendium.put("burn", "0_fm'latgh");
        compendium.put("skin", "0_ftaghu");
        compendium.put("boundary", "0_ftaghu");
        compendium.put("here", "0_geb");
        compendium.put("father", "0_gnaiih");
        compendium.put("children", "0_gof'nn");
        compendium.put("grant", "0_goka");
        compendium.put("wish", "0_gotha");
        compendium.put("lost one", "0_grah'n");
        compendium.put("larva", "0_grah'n");
        compendium.put("priest", "0_hafh'drn");
        compendium.put("summoner", "0_hafh'drn");
        compendium.put("now", "0_hai");
        compendium.put("heretic", "0_hlirgh");
        compendium.put("followers", "0_hrii");
        compendium.put("born of", "0_hupadgh");
        compendium.put("expect", "0_ilyaa");
        compendium.put("await", "0_ilyaa");
        compendium.put("share", "0_k'yarnak");
        compendium.put("exchange", "0_k'yarnak");
        compendium.put("understand", "0_kadishtu");
        compendium.put("know", "0_kadishtu");
        compendium.put("question", "0_kn'a");
        compendium.put("on pain of", "0_li'hee");
        compendium.put("at", "0_llll");
        compendium.put("beside", "0_llll");
        compendium.put("mind", "0_lloig");
        compendium.put("psyche", "0_lloig");
        compendium.put("dream", "0_lw'nafh");
        compendium.put("transmit", "0_lw'nafh");
        compendium.put("worthless", "0_mnahn'");
        compendium.put("death", "0_n'gha");
        compendium.put("darkness", "0_n'ghft");
        compendium.put("threshold", "0_nglui");
        compendium.put("anything", "0_nilgh'ri");
        compendium.put("everything", "0_nilgh'ri");
        compendium.put("come", "0_nog");
        compendium.put("head", "0_nw");
        compendium.put("place", "0_nw");
        compendium.put("visit", "0_ooboshu");
        compendium.put("soul", "0_orr'e");
        compendium.put("spirit", "0_orr'e");
        compendium.put("realm of information", "0_phlegeth");
        compendium.put("secret", "0_r'luh");
        compendium.put("hidden", "0_r'luh");
        compendium.put("religon", "0_ron");
        compendium.put("cult", "0_ron");
        compendium.put("pact", "0_s'uhn");
        compendium.put("share space", "0_sgn'wahl");
        compendium.put("realm of dreams", "0_shagg");
        compendium.put("realm of darkness", "0_shogg");
        compendium.put("invite", "0_sll'ha");
        compendium.put("ask", "0_stell'bsna");
        compendium.put("pray for", "0_sll'ha");
        compendium.put("eternity", "0_syha'h");
        compendium.put("promise", "0_tharanak");
        compendium.put("bring", "0_tharanak");
        compendium.put("tremble", "0_throd");
        compendium.put("finish spell", "0_uaaah");
        compendium.put("people", "0_uh'e");
        compendium.put("crowd", "0_uh'e");
        compendium.put("summon", "0_uln");
        compendium.put("pray to", "0_vulgtlagin");
        compendium.put("prayer", "0_vulgtm");
        compendium.put("reside in", "0_wgah'n");
        compendium.put("control", "0_wgah'n");
        compendium.put("amen", "0_y'hah");
        compendium.put("I", "0_ya");
        compendium.put("lift spell", "0_zhro");
        compendium.put("time of", "1_-yar");
        compendium.put("moment", "1_-yar");
        compendium.put("my", "1_y-");
        compendium.put("they", "1_f'-");
        compendium.put("their", "1_f'-");
        compendium.put("it", "1_h'-");
        compendium.put("its", "1_h'-");
        compendium.put("yet", "2_mg");
        compendium.put("not", "3_nafl-");
        compendium.put("and", "3_ng-");
        compendium.put("watch", "1_nnn-");
        compendium.put("protect", "1_nnn-");
        compendium.put("servant of", "1_-nyth");
        compendium.put("force from", "1_-or");
        compendium.put("aspect of", "1_-or");
        compendium.put("native of", "1_-oth");
        compendium.put("over", "1_ph'-");
        compendium.put("beyond", "1_ph'-");
        compendium.put("notify", "0_shtunggli");
        compendium.put("contact", "0_shtunggli");
        compendium.put("realm of Earth", "0_shugg");
        compendium.put("we", "3_c-");
        compendium.put("our", "3_c-");
        compendium.put("with hai", "0_ep");
    }
    public static String translate(String text) {
        if (text == null || text.isEmpty())
            return null;
        final StringBuilder out = new StringBuilder();
        text = text.replaceAll(" +", " ").toLowerCase();
        boolean first = true;
        for (String word : text.split(" ")) {
            if (word.trim().isEmpty())
                continue;
            if (!compendium.containsKey(word)) {
                if (first)
                    first = false;
                else
                    out.append(" ");
                out.append(word);
                continue;
            }
            final String v = compendium.get(word);
            char c = v.charAt(0);
            switch (c) {
                //suffix, prefix
                case '1', '2', '3' -> out.append(v.substring(2));
                //word
                case '0' -> {
                    if (first)
                        first = false;
                    else
                        out.append(" ");
                    out.append(v.substring(2));
                }
            }
        }
        return out.toString();
    }
}
