
import java.util.Scanner;

public class ClinicQConsoleDemo {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        ClinicSystem clinic = new ClinicSystem();
        ConsoleMenu menu = new ConsoleMenu(scanner, clinic);

        menu.start();

        scanner.close();
    }
}

// Displays the menu and receives user input
class ConsoleMenu {

    private Scanner scanner;
    private ClinicSystem clinic;

    ConsoleMenu(Scanner scanner, ClinicSystem clinic) {
        this.scanner = scanner;
        this.clinic = clinic;
    }

    void start() {

        System.out.println("================================");
        System.out.println("          CLINICQ SYSTEM");
        System.out.println("================================");
        System.out.println("1. Book Appointment");
        System.out.println("2. Exit");
        System.out.print("Enter choice: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 1) {
            bookAppointment();
        } else {
            System.out.println("Thank you for using ClinicQ!");
        }
    }

    private void bookAppointment() {

        System.out.print("\nEnter Patient Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Barangay: ");
        String barangay = scanner.nextLine();

        System.out.println("\nConsultation Types:");
        System.out.println("1. General Consultation");
        System.out.println("2. Dental Consultation");
        System.out.println("3. Medical Certificate");
        System.out.print("Choose consultation: ");

        int serviceChoice = scanner.nextInt();

        System.out.println("\nAvailable Appointment Slots:");
        System.out.println("1. 8:00 AM");
        System.out.println("2. 8:30 AM");
        System.out.println("3. 9:00 AM");
        System.out.print("Choose appointment slot: ");

        int slotChoice = scanner.nextInt();

        clinic.bookAppointment(
            name, barangay, serviceChoice, slotChoice
        );
    }
}

// Coordinates the appointment process
class ClinicSystem {

    private AppointmentSlot slots = new AppointmentSlot();

    void bookAppointment(
        String name,
        String barangay,
        int serviceChoice,
        int slotChoice
    ) {

        Patient patient = new Patient(name, barangay);

        ConsultationService service = new ConsultationService();

        String consultation = service.getServiceName(serviceChoice);

        if (consultation.equals("Invalid")) {
            System.out.println("\nInvalid consultation type.");
            return;
        }

        String time = slots.bookSlot(slotChoice);

        if (time.equals("Unavailable")) {
            System.out.println("\nInvalid or unavailable slot.");
            return;
        }

        Appointment appointment =
            new Appointment(patient, consultation, time);

        appointment.displayDetails();
    }
}

// Stores patient information
class Patient {

    private String name;
    private String barangay;

    Patient(String name, String barangay) {
        this.name = name;
        this.barangay = barangay;
    }

    String getName() {
        return name;
    }

    String getBarangay() {
        return barangay;
    }
}

// Handles consultation selection
class ConsultationService {

    String getServiceName(int choice) {

        if (choice == 1) {
            return "General Consultation";
        } else if (choice == 2) {
            return "Dental Consultation";
        } else if (choice == 3) {
            return "Medical Certificate";
        }

        return "Invalid";
    }
}

// Handles appointment slot availability
class AppointmentSlot {

    private String[] times = {
        "8:00 AM",
        "8:30 AM",
        "9:00 AM"
    };

    private boolean[] booked = {
        false, false, false
    };

    String bookSlot(int choice) {

        if (choice < 1 || choice > times.length) {
            return "Unavailable";
        }

        if (booked[choice - 1]) {
            return "Unavailable";
        }

        booked[choice - 1] = true;

        return times[choice - 1];
    }
}

// Stores and displays appointment details
class Appointment {

    private Patient patient;
    private String consultation;
    private String time;

    Appointment(Patient patient, String consultation, String time) {
        this.patient = patient;
        this.consultation = consultation;
        this.time = time;
    }

    void displayDetails() {

        System.out.println("\n================================");
        System.out.println("      APPOINTMENT CONFIRMED");
        System.out.println("================================");
        System.out.println("Patient Name: " + patient.getName());
        System.out.println("Barangay: " + patient.getBarangay());
        System.out.println("Consultation: " + consultation);
        System.out.println("Appointment Time: " + time);
        System.out.println("================================");
    }
}

