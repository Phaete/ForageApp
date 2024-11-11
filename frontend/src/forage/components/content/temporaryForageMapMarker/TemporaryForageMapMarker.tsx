import {TemporaryForageMapMarkerProps} from "./TemporaryForageMapMarkerProps.ts"
import {useEffect, useRef, useState} from "react";
import {Marker, Popup} from "react-leaflet";
import L, {DragEndEvent} from "leaflet";
import ForageMapItemEditor from "../../../pages/mapView/forageMapItemEditor/ForageMapItemEditor.tsx";
import {GeoPosition} from "../../../types/GeoPosition.ts";

const TemporaryForageMapMarker = (props: TemporaryForageMapMarkerProps) => {

	const [position, setPosition] = useState<GeoPosition>(props.position)

	const markerRef = useRef(null)

	useEffect(() => {
		if (position) {
			// @ts-expect-error - At the time of execution, markerRef.current is not null
			markerRef.current.openPopup()
		}
	});

	function handleDragEndEvent(event: DragEndEvent) {
		setPosition({latitude: event.target.getLatLng().lat, longitude: event.target.getLatLng().lng})
		// @ts-expect-error - At the time of execution, markerRef.current is not null
		markerRef.current.openPopup()
	}

	return position ? (
		<Marker
			position={[position.latitude, position.longitude]}
			ref={markerRef}
			icon={
				L.icon({
					iconUrl: "https://res.cloudinary.com/dpahk173t/image/upload/v1730207562/JamIcons_pin_alt.svg_gyqze5.png",
					iconSize: [30, 30],
					iconAnchor: [15, 30],
					popupAnchor: [0, -30]
				})
			}
			draggable={true}
			eventHandlers={{dragend: handleDragEndEvent}}>
			<Popup>
				Lat: {position.latitude.toFixed(4)}, Lng: {position.longitude.toFixed(4)}
				<ForageMapItemEditor
					forageMapItemPosition={position}
					fetchForageMapItems={props.fetchForageMapItems}
					forageWikiItems={props.forageWikiItems}
					customMarker={props.customMarker}
					setAddForageMapItem={props.setAddForageMapItem}
					setDetailedForageMapItem={props.setDetailedForageMapItem}
					user={props.user}/>
			</Popup>
		</Marker>
	) : null
}

export default TemporaryForageMapMarker;