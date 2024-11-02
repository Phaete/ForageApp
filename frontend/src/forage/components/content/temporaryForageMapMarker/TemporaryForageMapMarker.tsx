import {TemporaryForageMapMarkerProps} from "./TemporaryForageMapMarkerProps.ts"
import {useEffect, useRef, useState} from "react";
import {Marker, Popup, useMap} from "react-leaflet";
import L, {DragEndEvent} from "leaflet";
import ForageMapItemEditor from "../../../pages/mapView/forageMapItemEditor/ForageMapItemEditor.tsx";
import {GeoPosition} from "../../../types/GeoPosition.ts";

const TemporaryForageMapMarker = (props: TemporaryForageMapMarkerProps) => {

	const [position, setPosition] = useState<GeoPosition>(props.position)

	const markerRef = useRef(null)
	const map = useMap()

	useEffect(() => {
		if (position) {
			markerRef.current?.openPopup()
			map.flyTo([position.latitude+0.0005, position.longitude])
		}
	}, []);

	function handleDragEndEvent(event: DragEndEvent) {
		setPosition({latitude: event.target.getLatLng().lat, longitude: event.target.getLatLng().lng})
		markerRef.current?.openPopup()
	}

	return position ? (
		<Marker
			position={[position.latitude, position.longitude]}
			ref={markerRef}
			icon={
				L.icon({
					iconUrl: "https://res.cloudinary.com/dpahk173t/image/upload/v1730207562/JamIcons_pin_alt.svg_gyqze5.png",
					iconSize: [60, 60],
					iconAnchor: [30, 60],
					popupAnchor: [0, -60]
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
					setAddForageMapItem={props.setAddForageMapItem}/>
			</Popup>
		</Marker>
	) : null
}

export default TemporaryForageMapMarker;