import { UserDto } from "./userDto";
import { Vehicle } from "./vehicle";

export interface Booking {
    id: number,
    vehicle: Vehicle,
    user: UserDto,
    bookingDate: Date,
    finishDate: Date,
    serviceType: string,
    isReady: boolean,
    isConfirmed: boolean,
    price: number,
    description: string,
}