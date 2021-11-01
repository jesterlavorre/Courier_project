package rs.etf.sab.student;

import rs.etf.sab.operations.*;
import rs.etf.sab.tests.TestHandler;
import rs.etf.sab.tests.TestRunner;
import rs.etf.sab.operations.GeneralOperations;

public class StudentMain {

    public static void main(String[] args) {
        AddressOperations addressOperations = new AddressOperations(); // Change this to your implementation.
        CityOperations cityOperations = new CityOperations(); // Do it for all classes.
        CourierOperations courierOperations = new CourierOperations(); // e.g. = new MyDistrictOperations();
        CourierRequestOperation courierRequestOperation = new CourierRequestOperation();
        DriveOperation driveOperation = new DriveOperation();
        GeneralOperations generalOperations = new GeneralOperations1();
        PackageOperations packageOperations = new PackageOperations();
        StockroomOperations stockroomOperations = new StockroomOperations();
        UserOperations userOperations = new UserOperations();
        VehicleOperations vehicleOperations = new VehicleOperations();
        

        
        TestHandler.createInstance(
                addressOperations,
                cityOperations,
                courierOperations,
                courierRequestOperation,
                driveOperation,
                generalOperations,
                packageOperations,
                stockroomOperations,
                userOperations,
                vehicleOperations);

        TestRunner.runTests();
    }
}
