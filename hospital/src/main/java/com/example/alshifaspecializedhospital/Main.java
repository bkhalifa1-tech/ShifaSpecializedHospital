package com.example.alshifaspecializedhospital;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        HospitalSystem hospitalSystem = HospitalSystem.getInstance();
        Scanner inputScanner = new Scanner(System.in);

        // --- حلقة do-while لإعادة طلب اسم الملف عند الخطأ ---
        boolean isLoaded = false;
        do {
            System.out.print("أدخل مسار ملف الأطباء: ");
            String doctorsFilePath = inputScanner.nextLine().trim();

            isLoaded = hospitalSystem.loadDoctorsFromFile(doctorsFilePath);

            if (!isLoaded) {
                System.out.println("يرجى إعادة إدخال اسم أو مسار الملف بشكل صحيح.\n");
            }
        } while (!isLoaded);

        int selectedOption = 0;

        do {
            printMainMenu();

            // --- قراءة الرقم مباشرة بدون دالة خارجية ---
            System.out.print("اختر: ");
            selectedOption = inputScanner.nextInt();
            inputScanner.nextLine(); // لتنظيف زر ה-Enter من الذاكرة لكي لا يؤثر على باقي المدخلات

            switch (selectedOption) {
                case 1:
                    hospitalSystem.registerPatient(inputScanner);
                    break;

                case 2:
                    hospitalSystem.addMedicalStaff(inputScanner);
                    break;

                case 3:
                    try {
                        hospitalSystem.scheduleAppointment(inputScanner);
                    } catch (DoctorUnavailableException exception) {
                        System.out.println(exception.getMessage());
                    } catch (IllegalArgumentException exception) {
                        System.out.println(exception.getMessage());
                    }
                    break;

                case 4:
                    hospitalSystem.viewHospitalData(inputScanner);
                    break;

                case 5:
                    hospitalSystem.manageAppointment(inputScanner);
                    break;

                case 6:
                    hospitalSystem.manageMedicalRecord(inputScanner);
                    break;

                case 7:
                    hospitalSystem.showReports(inputScanner);
                    break;

                case 8:
                    System.out.println("جارٍ حفظ البيانات...");
                    hospitalSystem.saveAppointmentsToFile("appointments.txt");
                    System.out.println("تم إنهاء البرنامج.");
                    break;

                default:
                    System.out.println("اختر رقمًا من 1 إلى 8.");
            }

        } while (selectedOption != 8);

        inputScanner.close();
    }

    private static void printMainMenu() {
        System.out.println("\n========== نظام المستشفى الذكي ==========");
        System.out.println("1. تسجيل مريض جديد");
        System.out.println("2. إضافة كادر طبي");
        System.out.println("3. حجز موعد");
        System.out.println("4. عرض بيانات المستشفى");
        System.out.println("5. إدارة موعد");
        System.out.println("6. السجلات الطبية");
        System.out.println("7. التقارير");
        System.out.println("8. حفظ وخروج");
    }
}