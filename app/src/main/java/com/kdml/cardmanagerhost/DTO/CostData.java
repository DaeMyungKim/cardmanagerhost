package com.kdml.cardmanagerhost.DTO;

/**
 * Created by 김대명 on 2016-08-08.
 */
public class CostData {
    private String cardName;
    private String yearMonth;
    private String cost;
    private String email;
    private String name;
    private String phone;
    private String year;
    private String month;

    public CostData(){}

    public String getMonth() {
        return month;
    }

    public String getYear() {
        return year;
    }

    public CostData(String cardName, String yearMonth, String cost, String email, String name, String phone) {
        this.cardName = cardName;
        this.cost = cost;
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.yearMonth = yearMonth;
        setYearMonth(yearMonth);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
        if(yearMonth.length() == 6)
        {
            year = yearMonth.substring(0,4);
            month= yearMonth.substring(4,6);
        }else if(yearMonth.length() == 5)
        {
            year = yearMonth.substring(0,4);
            month= yearMonth.substring(4,5);
        }
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
