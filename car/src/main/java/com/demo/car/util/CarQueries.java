package com.demo.car.util;

public class CarQueries {

    public static String SEARCH_BY_DETAILS =
        "SELECT ID, MODEL, BRAND, ENGINE_TYPE, COLOR, NO_OF_SEATS, TANK_SIZE, MILEAGE, PRICE FROM CAR_DETAILS WHERE "
            + "BRAND =:brand and ENGINE_TYPE = :engineType and NO_OF_SEATS <= :noOfSeatsR and NO_OF_SEATS >= "
            + ":noOfSeatsL and TANK_SIZE <= :tankSizeR and TANK_SIZE >= :tankSizeL and MILEAGE >= :mileageL and "
            + "MILEAGE <= :mileageR";

    public static String FIND_BY_ID =
        "SELECT * FROM CAR_DETAILS WHERE ID = :id";

}
