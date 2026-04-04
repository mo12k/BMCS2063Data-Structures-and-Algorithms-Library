package dao;

import adt.DoublyLinkedList;
import adt.ListInterface;
import entity.Reservation;
import java.io.*;

public class ReservationDAO {
    private static final String FILE_NAME = "reservation.dat";

    public void saveToFile(ListInterface<Reservation> reservationList) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(reservationList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public ListInterface<Reservation> retrieveFromFile() {
        ListInterface<Reservation> reservationList = new DoublyLinkedList<>();
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return reservationList;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            reservationList = (ListInterface<Reservation>) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return reservationList;
    }
}