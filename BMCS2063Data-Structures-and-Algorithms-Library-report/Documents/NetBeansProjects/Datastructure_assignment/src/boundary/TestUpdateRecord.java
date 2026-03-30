import dao.BorrowRecordDAO;
import adt.ListInterface;
import entity.BorrowRecord;

public class TestUpdateRecord {
    public static void main(String[] args) {

        BorrowRecordDAO dao = new BorrowRecordDAO();
        ListInterface<BorrowRecord> recordList = dao.retrieveFromFile();

        boolean found = false;

        for (int i = 1; i <= recordList.size(); i++) {
            BorrowRecord record = recordList.get(i);

            if (record != null
                    && record.getBorrowerID().equalsIgnoreCase("ST001")
                    && record.getBookID().equalsIgnoreCase("B0001")) {

                
                record.setBorrowDate("2026-02-01");
                record.setExpiryDate("2026-03-01");  
                record.setStatus("EXPIRED");

                System.out.println("Record updated:");
                System.out.println(record);

                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Record not found.");
        }

        
        dao.saveToFile(recordList);
    }
}
