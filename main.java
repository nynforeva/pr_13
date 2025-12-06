public class RideOrderSystem {

    public enum State {
        IDLE,
        CAR_SELECTED,
        ORDER_CONFIRMED,
        CAR_ARRIVED,
        IN_TRIP,
        TRIP_COMPLETED,
        TRIP_CANCELLED
    }

    public static class RideOrderStateMachine {

        private State state = State.IDLE;

        public State getState() {
            return state;
        }

        public void selectCar() {
            if (state == State.IDLE) {
                state = State.CAR_SELECTED;
                System.out.println("-> Автомобиль выбран (CAR_SELECTED)");
            } else {
                invalidAction("selectCar");
            }
        }

        public void changeCar() {
            if (state == State.CAR_SELECTED) {
                System.out.println("-> Автомобиль изменен (CAR_SELECTED)");
            } else {
                invalidAction("changeCar");
            }
        }

        public void confirmOrder() {
            if (state == State.CAR_SELECTED) {
                state = State.ORDER_CONFIRMED;
                System.out.println("-> Заказ подтвержден (ORDER_CONFIRMED)");
            } else {
                invalidAction("confirmOrder");
            }
        }

        public void carArrived() {
            if (state == State.ORDER_CONFIRMED) {
                state = State.CAR_ARRIVED;
                System.out.println("-> Машина прибыла (CAR_ARRIVED)");
            } else {
                invalidAction("carArrived");
            }
        }

        public void startTrip() {
            if (state == State.CAR_ARRIVED) {
                state = State.IN_TRIP;
                System.out.println("-> Поездка началась (IN_TRIP)");
            } else {
                invalidAction("startTrip");
            }
        }

        public void completeTrip() {
            if (state == State.IN_TRIP) {
                state = State.TRIP_COMPLETED;
                System.out.println("-> Поездка завершена (TRIP_COMPLETED)");
            } else {
                invalidAction("completeTrip");
            }
        }

        public void pay(boolean success) {
            if (state == State.TRIP_COMPLETED) {
                if (success) {
                    System.out.println("-> Оплата успешна (IDLE)");
                    state = State.IDLE;
                } else {
                    System.out.println("-> Ошибка оплаты. Повторите попытку.");
                }
            } else {
                invalidAction("pay");
            }
        }

        public void cancel() {
            if (state != State.IN_TRIP && state != State.TRIP_COMPLETED) {
                state = State.TRIP_CANCELLED;
                System.out.println("-> Заказ отменен (TRIP_CANCELLED)");
            } else {
                invalidAction("cancel");
            }
        }

        public void delay() {
            if (state == State.ORDER_CONFIRMED) {
                System.out.println("-> Автомобиль задерживается...");
            } else {
                invalidAction("delay");
            }
        }

        private void invalidAction(String action) {
            System.out.println("!! Действие '" + action + "' невозможно в состоянии " + state);
        }
    }

    public static void main(String[] args) {

        RideOrderStateMachine order = new RideOrderStateMachine();

        order.selectCar();
        order.changeCar();
        order.confirmOrder();

        order.delay();
        order.carArrived();

        order.startTrip();
        order.completeTrip();

        order.pay(false);
        order.pay(true);
    }
}
