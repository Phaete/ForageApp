import 'leaflet/dist/leaflet.css'
import {MapContainer, TileLayer, useMapEvents} from 'react-leaflet'
import L from "leaflet";
import * as React from "react";
import {useEffect, useState} from "react";

export default function MapView() {
	const [mapCenter, setMapCenter] = useState<{ position: [number, number], zoom: number }>(
		{position: [51, 10], zoom: 6}
	)

	const PositionUpdateEvent = () => {
		useMapEvents({
			move: (e) => {
				setMapCenter({position: [e.target.getCenter().lat, e.target.getCenter().lng], zoom: e.target.getZoom()})
			}
		})
		return null;
	}


	function InitialLoad() {
		const map = useMapEvents({
			locationfound(e) {
				map.flyTo(e.latlng, map.getZoom()+3);
				setMapCenter({position: [e.latlng.lat, e.latlng.lng], zoom: map.getZoom()});
			}
		});

		useEffect(() => {
			map.locate();
		}, [map]);

		return null;
	}

	React.useEffect(() => {
		L.Icon.Default.mergeOptions({
			iconRetinaUrl: "leaflet/dist/images/marker-icon-2x.png",
			iconUrl: "leaflet/dist/images/marker-icon.png",
			shadowUrl: "leaflet/dist/images/marker-shadow.png"
		});
	}, []);

	return (
		<div className={"pos-relative"}>
			<div>
				Lat: {mapCenter.position[0].toFixed(4)}, Lng: {mapCenter.position[1].toFixed(4)}
			</div>
			<MapContainer className={"map-size-100"} center={mapCenter.position} zoom={mapCenter.zoom}
						  zoomControl={false}>
				<TileLayer
					attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
					url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
					keepBuffer={8}
				/>
				<InitialLoad />
				<PositionUpdateEvent/>
			</MapContainer>
		</div>
	)
}