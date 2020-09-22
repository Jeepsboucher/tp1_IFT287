package tp1;

public enum ConnectibleType {
    AIR_CONNECTIBLE("AirConnectible"),
    ALVEOLI("Alveoli"),
    ARTERY("Artery"),
    ATRIUM("Atrium"),
    BI_DUCT("BiDuct"),
    CAPILLARIES("Capillaries"),
    DEVERSING_DUCT("DeversingDuct"),
    DIGESTIVE_TRACT("DigestiveTract"),
    DUCT("Duct"),
    DUCT_OVERFLOWABLE_JUNCTION("DuctOverflowableJunction"),
    DUODENUM_TRACT("DuodenumTract"),
    INNER_GALLBLADDER("InnerGallbladder"),
    NOSE("Nose"),
    RECTUM_TRACT("RectumTract"),
    SALIVARY_DUCT("SalivaryDuct"),
    STOMACH_TRACT("StomachTract"),
    VEIN("Vein"),
    VENTRICLE("Ventricle");
    
    private final String typeName;
    
    private ConnectibleType(String typeName) {
      this.typeName = typeName;
    }

    public String getTypeName() {
      return this.typeName;
    }
}
