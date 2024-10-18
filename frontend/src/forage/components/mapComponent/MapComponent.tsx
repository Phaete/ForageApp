import "./MapComponent.css"
import 'leaflet/dist/leaflet.css'
import {MapContainer, TileLayer} from 'react-leaflet'
import L from "leaflet";
import * as React from "react";
import {useState} from "react";
import axios from "axios";

export default function MapComponent()  {

	const [mapCenter, setMapCenter] = useState<{ position: [number, number], zoom: number }>(
		{ position: [51, 10], zoom: 6})

	React.useEffect(() => {
		L.Icon.Default.mergeOptions({
			iconRetinaUrl: "leaflet/dist/images/marker-icon-2x.png",
			iconUrl: "leaflet/dist/images/marker-icon.png",
			shadowUrl: "leaflet/dist/images/marker-shadow.png"
		});

		navigator.geolocation.getCurrentPosition(
			(position) => {
				L.map("map").setView([position.coords.latitude, position.coords.longitude], 13);
			setMapCenter({position: [position.coords.latitude, position.coords.longitude], zoom: 13});
			},
			() => {
				const city = prompt("Please enter your city or town: ");
				if (city) {
					getCityPosition(city)
				} else {
					setMapCenter({ position: [51, 10], zoom: 6});
				}
		})
	}, []);


	async function getCityPosition(city: string) {
		try {
			axios.create({
				headers: {
					"User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:131.0) Gecko/20100101 Firefox/131.0",
					"Referer": "https://nominatim.openstreetmap.org/ui/search.html"
				}
			})
			const response = await axios.get(`https://nominatim.openstreetmap.org/search?q=${encodeURIComponent(city)}&polygon_geojson=1&format=jsonv2`);
			const data = response.data[0];
			const location = data.latlon;
			console.log([location.lat, location.lng])
			setMapCenter({position: [location.lat, location.lng], zoom: 13});
		} catch (error) {
			console.error("Error getting city position:", error);
		}
	}

	return (
		<MapContainer className={"map-size-100"} center={mapCenter.position} zoom={mapCenter.zoom}>
			<TileLayer
				attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
				url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
			/>
		</MapContainer>
	)
}