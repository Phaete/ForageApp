import {GeoPosition} from "../../../types/GeoPosition.ts";

export type UserMapMarkerProps = {
	userPosition: GeoPosition,
	setUserPosition: (userPosition: GeoPosition) => void
	zoom: number
}