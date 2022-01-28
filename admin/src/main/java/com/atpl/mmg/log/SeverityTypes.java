package com.atpl.mmg.log;

/**
 *
 * @author Raghu M
 */
public enum SeverityTypes {
    
    EMERGENCY(1),
    ALERT(2),
    CRITICAL(3),
    WARNING(4),
    NOTICE(5),
    INFORMATIONAL(6),
    DEBUG(7);
    
    private final int sevType;
    
    private SeverityTypes(int s) {
        sevType = s;
    }
    
    public boolean equalsName(int type) {
        return sevType == type;
    }
    
    public int getSev() {
        return sevType;
    }
}