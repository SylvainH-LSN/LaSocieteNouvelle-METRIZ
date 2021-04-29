/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meta;

/**
 *
 * @author SylvainPro
 */

public enum EconomicBranch {
    
    A01("01","Culture et production animale, chasse et services annexes"),
    A02("02","Sylviculture et exploitation forestière"),
    A03("03","Pêche et aquaculture"),
    B05("05","Extraction de houille et de lignite"),
    B06("06","Extraction d'hydrocarbures"),
    B07("07","Extraction de minerais métalliques"),
    B08("08","Autres industries extractives"),
    B09("09","Services de soutien aux industries extractives"),
    C10("10","Industries alimentaires"),
    C11("11","Fabrication de boissons"),
    C12("12","Fabrication de produits à base de tabac"),
    C13("13","Fabrication de textiles"),
    C14("14","Industrie de l'habillement"),
    C15("15","Industrie du cuir et de la chaussure"),
    C16("16","Travail du bois et fabrication d'articles en bois et en liège, à l'exception des meubles , fabrication d'articles en vannerie et sparterie"),
    C17("17","Industrie du papier et du carton"),
    C18("18","Imprimerie et reproduction d'enregistrements"),
    C19("19","Cokéfaction et raffinage"),
    C20("20","Industrie chimique"),
    C21("21","Industrie pharmaceutique"),
    C22("22","Fabrication de produits en caoutchouc et en plastique"),
    C23("23","Fabrication d'autres produits minéraux non métalliques"),
    C24("24","Métallurgie"),
    C25("25","Fabrication de produits métalliques, à l'exception des machines et des équipements"),
    C26("26","Fabrication de produits informatiques, électroniques et optiques"),
    C27("27","Fabrication d'équipements électriques"),
    C28("28","Fabrication de machines et équipements n.c.a."),
    C29("29","Industrie automobile"),
    C30("30","Fabrication d'autres matériels de transport"),
    C31("31","Fabrication de meubles"),
    C32("32","Autres industries manufacturières"),
    C33("33","Réparation et installation de machines et d'équipements"),
    D35("35","Production et distribution d'électricité, de gaz, de vapeur et d'air conditionné"),
    E36("36","Captage, traitement et distribution d'eau"),
    E37("37","Collecte et traitement des eaux usées"),
    E38("38","Collecte, traitement et élimination des déchets , récupération"),
    E39("39","Dépollution et autres services de gestion des déchets"),
    F41("41","Construction de bâtiments"),
    F42("42","Génie civil"),
    F43("43","Travaux de construction spécialisés"),
    G45("45","Commerce et réparation d'automobiles et de motocycles"),
    G46("46","Commerce de gros, à l'exception des automobiles et des motocycles"),
    G47("47","Commerce de détail, à l'exception des automobiles et des motocycles"),
    H49("49","Transports terrestres et transport par conduites"),
    H50("50","Transports par eau"),
    H51("51","Transports aériens"),
    H52("52","Entreposage et services auxiliaires des transports"),
    H53("53","Activités de poste et de courrier"),
    I55("55","Hébergement"),
    I56("56","Restauration"),
    J58("58","Édition"),
    J59("59","Production de films cinématographiques, de vidéo et de programmes de télévision , enregistrement sonore et édition musicale"),
    J60("60","Programmation et diffusion"),
    J61("61","Télécommunications"),
    J62("62","Programmation, conseil et autres activités informatiques"),
    J63("63","Services d'information"),
    K64("64","Activités des services financiers, hors assurance et caisses de retraite"),
    K65("65","Assurance"),
    K66("66","Activités auxiliaires de services financiers et d'assurance"),
    L68("68","Activités immobilières"),
    M69("69","Activités juridiques et comptables"),
    M70("70","Activités des sièges sociaux , conseil de gestion"),
    M71("71","Activités d'architecture et d'ingénierie , activités de contrôle et analyses techniques"),
    M72("72","Recherche-développement scientifique"),
    M73("73","Publicité et études de marché"),
    M74("74","Autres activités spécialisées, scientifiques et techniques"),
    M75("75","Activités vétérinaires"),
    N77("77","Activités de location et location-bail"),
    N78("78","Activités liées à l'emploi"),
    N79("79","Activités des agences de voyage, voyagistes, services de réservation et activités connexes"),
    N80("80","Enquêtes et sécurité"),
    N81("81","Services relatifs aux bâtiments et aménagement paysager"),
    N82("82","Activités administratives et autres activités de soutien aux entreprises"),
    O84("84","Administration publique et défense , sécurité sociale obligatoire"),
    P85("85","Enseignement"),
    Q86("86","Activités pour la santé humaine"),
    Q87("87","Hébergement médico-social et social"),
    Q88("88","Action sociale sans hébergement"),
    R90("90","Activités créatives, artistiques et de spectacle"),
    R91("91","Bibliothèques, archives, musées et autres activités culturelles"),
    R92("92","Organisation de jeux de hasard et d'argent"),
    R93("93","Activités sportives, récréatives et de loisirs"),
    S94("94","Activités des organisations associatives"),
    S95("95","Réparation d'ordinateurs et de biens personnels et domestiques"),
    S96("96","Autres services personnels"),
    T97("97","Activités des ménages en tant qu'employeurs de personnel domestique"),
    T98("98","Activités indifférenciées des ménages en tant que producteurs de biens et services pour usage propre"),
    U99("99","Activités des organisations et organismes extraterritoriaux"),
    TOTAL("00","Toutes"),;
    
    private final String code;
    private final String libelle;

    private EconomicBranch(String code, String libelle) {
        this.code = code;
        this.libelle = libelle;
    }

    public static Boolean isCodeCorrect(String code) {
        for (EconomicBranch branch : values()) {
            if (branch.code.equals(code)) {
                return true;
            }
        }
        return false;
    }
    
}
