import { Vehicle } from "./vehicle";

export interface Booking {
    vehicle: Vehicle,
    bookingDate: Date,
    finishDate: Date,
    serviceType: string,
    isReady: boolean,
    price: Number,
    description: string,
}