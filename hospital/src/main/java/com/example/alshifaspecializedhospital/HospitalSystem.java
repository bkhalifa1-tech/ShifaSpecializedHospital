package com.example.alshifaspecializedhospital;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class HospitalSystem {

    private static HospitalSystem instance;

    private final ArrayList<Person> people;
    private final ArrayList<Appointment> appointments;

    private HospitalSystem() {
        this.people = new ArrayList<>();
        this.appointments = new ArrayList<>();
    }

    public static HospitalSystem getInstance() {
        if (instance == null) {
            instance = new HospitalSystem();
        }
        return instance;
    }

    public boolean loadDoctorsFromFile(String fileName) {
        File doctorsFile = new File(fileName);

        if (!doctorsFile.exists()) {
            System.out.println("ملف الأطباء غير موجود: " + fileName);
            return false;
        }

        try (Scanner fileScanner = new Scanner(doctorsFile)) {
            while (fileScanner.hasNextLine()) {
                String doctorLine = fileScanner.nextLine().trim();

                if (doctorLine.isEmpty()) {
                    continue;
                }

                String[] parts = doctorLine.split("\\|");

                if (parts.length != 7) {
                    System.out.println("تم تجاهل سطر غير صحيح: " + doctorLine);
                    continue;
                }

                try {
                    String doctorName = parts[0].trim();
                    String doctorNationalId = parts[1].trim();
                    String doctorPhoneNumber = parts[2].trim();
                    double doctorSalary = Double.parseDouble(parts[3].trim());
                    int doctorWorkingHours = Integer.parseInt(parts[4].trim());
                    String doctorSpecialty = parts[5].trim();
                    double doctorExaminationFee = Double.parseDouble(parts[6].trim());

                    if (nationalIdExists(doctorNationalId)) {
                        System.out.println("تم تجاهل الطبيب لأن رقم الهوية مكرر: " + doctorNationalId);
                        continue;
                    }
                    Doctor loadedDoctor = new Doctor(doctorName, doctorNationalId, doctorPhoneNumber, doctorSalary, doctorWorkingHours, doctorSpecialty, doctorExaminationFee);
                    people.add(loadedDoctor);
                } catch (NumberFormatException exception) {
                    System.out.println("خطأ في القيم الرقمية في السطر: " + doctorLine);
                } catch (IllegalArgumentException exception) {
                    System.out.println("تعذر إنشاء الطبيب: " + exception.getMessage());
                }
            }

            System.out.println("تم تحميل بيانات الأطباء بنجاح.");
            return true;

        } catch (FileNotFoundException exception) {
            System.out.println("ملف الأطباء غير موجود: " + fileName);
            return false;
        }
    }

    public void registerPatient(Scanner inputScanner) {
        System.out.print("اسم المريض: ");
        String patientName = inputScanner.nextLine().trim();

        System.out.print("رقم الهوية: ");
        String patientNationalId = inputScanner.nextLine().trim();

        if (nationalIdExists(patientNationalId)) {
            System.out.println("رقم الهوية مسجل مسبقًا.");
            return;
        }

        System.out.print("رقم الهاتف: ");
        String patientPhoneNumber = inputScanner.nextLine().trim();

        System.out.print("فصيلة الدم: ");
        String patientBloodType = inputScanner.nextLine().trim();

        try {
            Patient newPatient = new Patient(patientName, patientNationalId, patientPhoneNumber, patientBloodType, "No diagnosis");
            people.add(newPatient);
            System.out.println("تم تسجيل المريض بنجاح. رقم المريض: " + newPatient.getId());
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void addMedicalStaff(Scanner inputScanner) {
        System.out.println("\n1. إضافة طبيب");
        System.out.println("2. إضافة ممرض");
        System.out.println("3. رجوع");
        int selectedOption = readInteger(inputScanner, "اختر: ");

        if (selectedOption == 1) {
            addDoctor(inputScanner);
        } else if (selectedOption == 2) {
            addNurse(inputScanner);
        } else if (selectedOption != 3) {
            System.out.println("اختر رقمًا من 1 إلى 3.");
        }
    }

    private void addDoctor(Scanner inputScanner) {
        System.out.print("اسم الطبيب: ");
        String doctorName = inputScanner.nextLine().trim();

        System.out.print("رقم الهوية: ");
        String doctorNationalId = inputScanner.nextLine().trim();

        if (nationalIdExists(doctorNationalId)) {
            System.out.println("رقم الهوية مسجل مسبقًا.");
            return;
        }

        System.out.print("رقم الهاتف: ");
        String doctorPhoneNumber = inputScanner.nextLine().trim();

        double doctorSalary = readDouble(inputScanner, "الراتب: ");
        int doctorWorkingHours = readInteger(inputScanner, "ساعات العمل: ");

        System.out.print("التخصص الطبي: ");
        String doctorSpecialty = inputScanner.nextLine().trim();

        double doctorExaminationFee = readDouble(inputScanner, "رسوم الكشف: ");

        try {
            Doctor newDoctor = new Doctor(doctorName, doctorNationalId, doctorPhoneNumber, doctorSalary, doctorWorkingHours, doctorSpecialty, doctorExaminationFee);
            people.add(newDoctor);
            System.out.println("تمت إضافة الطبيب بنجاح. رقم الطبيب: " + newDoctor.getId());
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private void addNurse(Scanner inputScanner) {
        System.out.print("اسم الممرض: ");
        String nurseName = inputScanner.nextLine().trim();

        System.out.print("رقم الهوية: ");
        String nurseNationalId = inputScanner.nextLine().trim();

        if (nationalIdExists(nurseNationalId)) {
            System.out.println("رقم الهوية مسجل مسبقًا.");
            return;
        }

        System.out.print("رقم الهاتف: ");
        String nursePhoneNumber = inputScanner.nextLine().trim();

        double nurseSalary = readDouble(inputScanner, "الراتب: ");
        int nurseWorkingHours = readInteger(inputScanner, "ساعات العمل: ");

        System.out.print("الغرفة: ");
        String nurseAssignedRoom = inputScanner.nextLine().trim();

        System.out.print("القسم: ");
        String nurseDepartment = inputScanner.nextLine().trim();

        try {
            Nurse newNurse = new Nurse(nurseName, nurseNationalId, nursePhoneNumber, nurseSalary, nurseWorkingHours, nurseAssignedRoom, nurseDepartment);
            people.add(newNurse);
            System.out.println("تمت إضافة الممرض بنجاح. رقم الممرض: " + newNurse.getId());
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void scheduleAppointment(Scanner inputScanner) throws DoctorUnavailableException {
        displayDoctors();
        int selectedDoctorId = readInteger(inputScanner, "رقم الطبيب: ");
        Doctor selectedDoctor = findDoctorById(selectedDoctorId);
        if (selectedDoctor == null) {
            System.out.println("الطبيب غير موجود.");
            return;
        }
        displayPatients();

        int selectedPatientId = readInteger(inputScanner, "رقم المريض: ");
        Patient selectedPatient = findPatientById(selectedPatientId);

        if (selectedPatient == null) {
            System.out.println("المريض غير موجود.");
            return;
        }
        int appointmentDate = readInteger(inputScanner, "أدخل التاريخ بالصيغة YYYYMMDD: ");
        int appointmentHour = readInteger(inputScanner, "أدخل الساعة من 0 إلى 23: ");

        Appointment newAppointment = new Appointment(selectedDoctor, selectedPatient, appointmentDate, appointmentHour);
        addAppointment(newAppointment);
        System.out.println("تم حجز الموعد بنجاح. رقم الموعد: " + newAppointment.getId());
    }

    public void addAppointment(Appointment appointment) throws DoctorUnavailableException {
        if (appointment == null) {
            throw new IllegalArgumentException("الموعد غير صالح.");
        }

        checkDoctorAvailability(appointment.getDoctor(), appointment.getAppointmentDate(), appointment.getAppointmentHour());
        appointments.add(appointment);
    }

    private void checkDoctorAvailability(Doctor selectedDoctor, int appointmentDate, int appointmentHour) throws DoctorUnavailableException {
        for (Appointment currentAppointment : appointments) {
            boolean sameDoctor = currentAppointment.getDoctor().getId() == selectedDoctor.getId();
            boolean sameDate = currentAppointment.getAppointmentDate() == appointmentDate;
            boolean sameHour = currentAppointment.getAppointmentHour() == appointmentHour;
            boolean appointmentIsActive = currentAppointment.getStatus() != AppointmentStatus.CANCELLED;

            if (sameDoctor && sameDate && sameHour && appointmentIsActive) {
                throw new DoctorUnavailableException("الطبيب لديه موعد آخر في هذا الوقت.");
            }
        }
    }

    public void viewHospitalData(Scanner inputScanner) {
        System.out.println("\n1. عرض الأطباء");
        System.out.println("2. عرض الممرضين");
        System.out.println("3. عرض المرضى");
        System.out.println("4. عرض المواعيد");
        System.out.println("5. رجوع");

        int selectedOption = readInteger(inputScanner, "اختر: ");
        switch (selectedOption) {
            case 1:
                displayDoctors();
                break;
            case 2:
                displayNurses();
                break;
            case 3:
                displayPatients();
                break;
            case 4:
                displayAppointments();
                break;
            case 5:
                break;
            default:
                System.out.println("اختر رقمًا من 1 إلى 5.");
        }
    }

    private void displayDoctors() {
        System.out.println("\n===== قائمة الأطباء =====");
        boolean doctorFound = false;

        for (Person currentPerson : people) {
            if (currentPerson instanceof Doctor) {
                currentPerson.print();
                doctorFound = true;
            }
        }
        if (!doctorFound) {
            System.out.println("لا يوجد أطباء مسجلون.");
        }
    }
    private void displayNurses() {
        System.out.println("\n===== قائمة الممرضين =====");

        boolean nurseFound = false;

        for (Person currentPerson : people) {
            if (currentPerson instanceof Nurse) {
                currentPerson.print();
                nurseFound = true;
            }
        }
        if (!nurseFound) {
            System.out.println("لا يوجد ممرضون مسجلون.");
        }
    }

    private void displayPatients() {
        System.out.println("\n===== قائمة المرضى =====");
        boolean patientFound = false;

        for (Person currentPerson : people) {
            if (currentPerson instanceof Patient) {
                currentPerson.print();
                patientFound = true;
            }
        }
        if (!patientFound) {
            System.out.println("لا يوجد مرضى مسجلون.");
        }
    }

    private void displayAppointments() {
        System.out.println("\n===== قائمة المواعيد =====");
        if (appointments.isEmpty()) {
            System.out.println("لا توجد مواعيد مسجلة.");
            return;
        }
        for (Appointment currentAppointment : appointments) {
            currentAppointment.print();
        }
    }

    public void manageAppointment(Scanner inputScanner) {
        displayAppointments();
        if (appointments.isEmpty()) {
            return;
        }
        int selectedAppointmentId = readInteger(inputScanner, "رقم الموعد: ");
        Appointment selectedAppointment = findAppointmentById(selectedAppointmentId);
        if (selectedAppointment == null) {
            System.out.println("الموعد غير موجود.");
            return;
        }
        System.out.println("1. بدء الموعد");
        System.out.println("2. إنهاء الموعد");
        System.out.println("3. إلغاء الموعد");
        System.out.println("4. رجوع");

        int selectedOption = readInteger(inputScanner, "اختر: ");
        try {
            if (selectedOption == 1) {
                selectedAppointment.startAppointment();
            } else if (selectedOption == 2) {
                selectedAppointment.completeAppointment();
            } else if (selectedOption == 3) {
                selectedAppointment.cancelAppointment();
            } else if (selectedOption == 4) {
                return;
            } else {
                System.out.println("اختر رقمًا من 1 إلى 4.");
                return;
            }
            System.out.println("حالة الموعد: " + selectedAppointment.getStatus());

        } catch (IllegalStateException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void manageMedicalRecord(Scanner inputScanner) {
        displayPatients();
        int selectedPatientId = readInteger(inputScanner, "رقم المريض: ");
        Patient selectedPatient = findPatientById(selectedPatientId);

        if (selectedPatient == null) {
            System.out.println("المريض غير موجود.");
            return;
        }
        System.out.println("1. عرض السجل الطبي");
        System.out.println("2. تعديل السجل الطبي");
        System.out.println("3. رجوع");

        int selectedOption = readInteger(inputScanner, "اختر: ");

        if (selectedOption == 1) {
            System.out.println("المريض: " + selectedPatient.getName());
            selectedPatient.getMedicalRecord().print();

        } else if (selectedOption == 2) {
            System.out.print("التشخيص الجديد: ");
            String updatedDiagnosis = inputScanner.nextLine().trim();
            selectedPatient.getMedicalRecord().setDiagnosis(updatedDiagnosis);
            System.out.println("تم تعديل السجل الطبي بنجاح.");
        } else if (selectedOption != 3) {
            System.out.println("اختر رقمًا من 1 إلى 3.");
        }
    }

    public void showReports(Scanner inputScanner) {
        System.out.println("\n1. المواعيد حسب التاريخ");
        System.out.println("2. المواعيد حسب الطبيب");
        System.out.println("3. المواعيد المكتملة");
        System.out.println("4. رجوع");

        int selectedOption = readInteger(inputScanner, "اختر: ");

        if (selectedOption == 1) {
            int requiredDate = readInteger(
                    inputScanner,
                    "أدخل التاريخ بالصيغة YYYYMMDD: "
            );
            boolean reportHasResults = false;
            for (Appointment currentAppointment : appointments) {
                if (currentAppointment.getAppointmentDate() == requiredDate) {
                    currentAppointment.print();
                    reportHasResults = true;
                }
            }
            if (!reportHasResults) {
                System.out.println("لا توجد مواعيد في هذا التاريخ.");
            }

        } else if (selectedOption == 2) {
            displayDoctors();
            int requiredDoctorId = readInteger(inputScanner, "رقم الطبيب: ");
            boolean reportHasResults = false;
            for (Appointment currentAppointment : appointments) {
                if (currentAppointment.getDoctor().getId() == requiredDoctorId) {
                    currentAppointment.print();
                    reportHasResults = true;
                }
            }
            if (!reportHasResults) {
                System.out.println("لا توجد مواعيد لهذا الطبيب.");
            }

        } else if (selectedOption == 3) {
            boolean reportHasResults = false;
            for (Appointment currentAppointment : appointments) {
                if (currentAppointment.getStatus() == AppointmentStatus.COMPLETED) {
                    currentAppointment.print();
                    reportHasResults = true;
                }
            }
            if (!reportHasResults) {
                System.out.println("لا توجد مواعيد مكتملة.");
            }

        } else if (selectedOption != 4) {
            System.out.println("اختر رقمًا من 1 إلى 4.");
        }
    }
    public void addPerson(Person person) {
        if (person == null) {
            throw new IllegalArgumentException("الشخص غير صالح.");
        }
        if (nationalIdExists(person.getNationalId())) {
            throw new IllegalArgumentException("رقم الهوية مسجل مسبقًا.");
        }
        people.add(person);
    }

    private Doctor findDoctorById(int doctorId) {
        for (Person currentPerson : people) {
            if (currentPerson instanceof Doctor && currentPerson.getId() == doctorId) {
                return (Doctor) currentPerson;
            }
        }
        return null;
    }

    private Patient findPatientById(int patientId) {
        for (Person currentPerson : people) {
            if (currentPerson instanceof Patient && currentPerson.getId() == patientId) {
                return (Patient) currentPerson;
            }
        }
        return null;
    }

    public Patient findPatientByNationalId(String nationalId) {
        if (nationalId == null) {
            return null;
        }

        for (Person currentPerson : people) {
            if (currentPerson instanceof Patient
                    && currentPerson.getNationalId().equalsIgnoreCase(nationalId.trim())) {
                return (Patient) currentPerson;
            }
        }

        return null;
    }

    public Appointment findAppointmentById(int appointmentId) {
        for (Appointment currentAppointment : appointments) {
            if (currentAppointment.getId() == appointmentId) {
                return currentAppointment;
            }
        }
        return null;
    }

    private boolean nationalIdExists(String nationalId) {
        if (nationalId == null) {
            return false;
        }
        for (Person currentPerson : people) {
            if (currentPerson.getNationalId().equalsIgnoreCase(nationalId.trim())) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Doctor> getDoctorsList() {
        ArrayList<Doctor> doctors = new ArrayList<>();

        for (Person person : people) {
            if (person instanceof Doctor) {
                doctors.add((Doctor) person);
            }
        }

        return doctors;
    }

    public List<Appointment> getAppointmentsList() {
        return Collections.unmodifiableList(appointments);
    }

    public void saveAppointmentsToFile(String fileName) {
        File appointmentsFile = new File(fileName);

        try (PrintWriter fileWriter = new PrintWriter(appointmentsFile)) {
            for (Appointment appointment : appointments) {
                Patient patient = appointment.getPatient();

                fileWriter.println(appointment.getId() + "|" + appointment.getDoctor().getId() + "|" + appointment.getDoctor().getName() + "|" + patient.getId() + "|" + patient.getName() + "|" + patient.getNationalId() + "|" + patient.getPhoneNumber() + "|" + patient.getBloodType() + "|"
                        + patient.getMedicalRecord().getDiagnosis() + "|" + appointment.getAppointmentDate() + "|" + appointment.getAppointmentHour() + "|" + appointment.getStatus());
            }
            System.out.println("تم حفظ المواعيد بنجاح.");

        } catch (FileNotFoundException exception) {
            System.out.println("تعذر إنشاء ملف المواعيد.");
        }
    }

    public void loadAppointmentsFromFile(String fileName) {
        File appointmentsFile = new File(fileName);

        if (!appointmentsFile.exists()) {
            return;
        }
        appointments.clear();

        try (Scanner fileScanner = new Scanner(appointmentsFile)) {
            while (fileScanner.hasNextLine()) {
                String appointmentLine = fileScanner.nextLine().trim();

                if (appointmentLine.isEmpty()) {
                    continue;
                }
                String[] parts = appointmentLine.split("\\|", -1);
                if (parts.length != 12) {
                    System.out.println("تم تجاهل موعد محفوظ بصيغة قديمة أو غير صحيحة.");
                    continue;
                }
                try {
                    int appointmentId = Integer.parseInt(parts[0].trim());
                    int doctorId = Integer.parseInt(parts[1].trim());
                    int patientId = Integer.parseInt(parts[3].trim());
                    String patientName = parts[4].trim();
                    String patientNationalId = parts[5].trim();
                    String patientPhone = parts[6].trim();
                    String patientBloodType = parts[7].trim();
                    String patientDiagnosis = parts[8].trim();
                    int appointmentDate = Integer.parseInt(parts[9].trim());
                    int appointmentHour = Integer.parseInt(parts[10].trim());
                    AppointmentStatus status = AppointmentStatus.valueOf(parts[11].trim());

                    Doctor doctor = findDoctorById(doctorId);

                    if (doctor == null) {
                        System.out.println("تم تجاهل موعد لأن الطبيب غير موجود.");
                        continue;
                    }
                    Patient patient = findPatientById(patientId);
                    if (patient == null) {
                        patient = findPatientByNationalId(patientNationalId);
                    }
                    if (patient == null) {
                        patient = new Patient(patientName, patientNationalId, patientPhone, patientBloodType, patientDiagnosis);
                        people.add(patient);
                    }
                    Appointment appointment = new Appointment(appointmentId, doctor, patient, appointmentDate, appointmentHour, status);
                    appointments.add(appointment);

                } catch (NumberFormatException exception) {
                    System.out.println("تعذر قراءة القيم الرقمية في موعد محفوظ.");
                } catch (IllegalArgumentException exception) {
                    System.out.println("تعذر قراءة الموعد: " + exception.getMessage());
                }
            }

            System.out.println("تم تحميل المواعيد المحفوظة.");

        } catch (FileNotFoundException exception) {
            System.out.println("ملف المواعيد غير موجود.");
        }
    }

    private int readInteger(Scanner inputScanner, String inputMessage) {
        while (true) {
            System.out.print(inputMessage);
            String enteredValue = inputScanner.nextLine().trim();

            try {
                return Integer.parseInt(enteredValue);
            } catch (NumberFormatException exception) {
                System.out.println("الرجاء إدخال رقم صحيح.");
            }
        }
    }

    private double readDouble(Scanner inputScanner, String inputMessage) {
        while (true) {
            System.out.print(inputMessage);
            String enteredValue = inputScanner.nextLine().trim();

            try {
                return Double.parseDouble(enteredValue);
            } catch (NumberFormatException exception) {
                System.out.println("الرجاء إدخال رقم صحيح.");
            }
        }
    }
}