import 'leaflet/dist/leaflet.css'
import { Map } from "leaflet"
import {GeoPosition} from "../../../types/GeoPosition.ts";

export type TrackingRequestMapEventProps = {
	mapRef: React.RefObject<Map>
	mapCenter: { position: GeoPosition, zoom: number }
	setMapCenter: (mapCenter: { position: GeoPosition, zoom: number }) => void
	setRequestTracking: (requestTracking: boolean) => void
	trackingAllowed: boolean
	setTrackingAllowed: (trackingAllowed: boolean) => void
	setUserPosition: (position: GeoPosition) => void,
	requestTracking: boolean
}