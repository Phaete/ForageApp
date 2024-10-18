import "./MapComponent.css"
import 'leaflet/dist/leaflet.css'
import {MapContainer, TileLayer} from 'react-leaflet'
import L from "leaflet";
import * as React from "react";
import {useState} from "react";

export default function MapComponent()  {

	const [mapCenter, setMapCenter] = useState<{ position: [number, number], zoom: number }>({position: [0, 0], zoom: 1})

	React.useEffect(() => {
		L.Icon.Default.mergeOptions({
			iconRetinaUrl: "leaflet/dist/images/marker-icon-2x.png",
			iconUrl: "leaflet/dist/images/marker-icon.png",
			shadowUrl: "leaflet/dist/images/marker-shadow.png"
		});
		setMapCenter({position: [51, 10], zoom: 6})
	}, [])

	return (
		<MapContainer className={"map-size-100"} center={mapCenter.position} zoom={mapCenter.zoom}>
			<TileLayer
				attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
				url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
			/>
		</MapContainer>
	)
}