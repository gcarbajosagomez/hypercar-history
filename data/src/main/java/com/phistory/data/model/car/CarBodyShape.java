package com.phistory.data.model.car;

/**
 * Enumeration of the different body shapes of a {@link Car}
 */
public enum CarBodyShape
{
	MICRO_CAR("microCar"),
	CITY_CAR("cityCar"),
	HATCHBACK("hatchBack"),
	FASTBACK("fastBack"),
	SHOOTING_BRAKE("shootingBrake"),
	STATION_WAGON("stationWagon"),
	FOUR_DOOR_SALOON("fourDoorSaloon"),
	GRAND_SALOON("grandSaloon"),
	LIMOUSINE("limousine"),
	COUPE("coupe"),
	GRAND_TOURER("grandTourer"),
	SUPERCAR("superCar"),
	SUPERCAR_ROADSTER("supercarRoadster"),
	MUSCLE_CAR("muscleCar"),
	CONVERTIBLE("convertible"),
	ROADSTER("roadster"),
	MINI_VAN("miniVan"),
	VAN("van"),
	SUV("SUV"),
	ALL_TERRAIN("allTerrain"),
	PICKUP_TRUCK("pickupTruck"),
    RACE_CAR("raceCar");

    private String name;

    CarBodyShape(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
