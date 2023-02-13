package com.example.test;

public class MenuDTO {
    private String TABLE_NUM;

    private String MENU_PRICE;

    private String MENU_NAME;

    public String getTABLE_NUM ()
    {
        return TABLE_NUM;
    }

    public void setTABLE_NUM (String TABLE_NUM)
    {
        this.TABLE_NUM = TABLE_NUM;
    }

    public String getMENU_PRICE ()
    {
        return MENU_PRICE;
    }

    public void setMENU_PRICE (String MENU_PRICE)
    {
        this.MENU_PRICE = MENU_PRICE;
    }

    public String getMENU_NAME ()
    {
        return MENU_NAME;
    }

    public void setMENU_NAME (String MENU_NAME)
    {
        this.MENU_NAME = MENU_NAME;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [TABLE_NUM = "+TABLE_NUM+", MENU_PRICE = "+MENU_PRICE+", MENU_NAME = "+MENU_NAME+"]";
    }
}
