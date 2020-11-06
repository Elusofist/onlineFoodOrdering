package hibernateORM.view;

import hibernateORM.data.facade.ManagerFacade;

public class ManagerView {

    public static void main(String[] args) {
        ManagerFacade managerFacade = new ManagerFacade();
        Thread managerThread = new Thread(managerFacade);
        managerThread.start();
    }
}