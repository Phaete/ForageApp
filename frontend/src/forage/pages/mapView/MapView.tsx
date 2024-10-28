import 'leaflet/dist/leaflet.css'
import {MapContainer, TileLayer, useMapEvents} from 'react-leaflet'
import L from "leaflet";
import * as React from "react";
import {useEffect, useState} from "react";
import axios from "axios";
import {MapViewProps} from "./MapViewProps.ts";
import {ForageMapItem} from "../../types/ForageMapItem.ts";

export default function MapView(props: Readonly<MapViewProps>) {
	const [mapCenter, setMapCenter] = useState<{ position: [number, number], zoom: number }>(
		{position: [51, 10], zoom: 6}
	)
	const [addNewForageMapItemFormVisible, setAddNewForageMapItemFormVisible] = useState<boolean>(false)

	const emptyForageMapItem = {
		id: crypto.randomUUID().toString(),
		forageWikiItem: {
			id: "",
			name: "",
			category: "",
			source: "",
			description: "",
			season: "",
			imageURLs: []
		},
		customMarker: {
			id: "",
			name: "",
			iconUrl: "",
			iconSize: [0, 0],
			iconAnchor: [0, 0],
			popupAnchor: [0, 0],
			popupText: ""
		},
		position: mapCenter.position,
		quantity: "",
		quality: "",
		notes: "",
	}

	const [forageMapItemToAdd, setForageMapItemToAdd] = useState<ForageMapItem>(emptyForageMapItem)

	function submitNewForageMapItem() {
		setAddNewForageMapItemFormVisible(false)

	}

	const [initialLoad, setInitialLoad] = useState<boolean>(false)

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
				setInitialLoad(true);
				map.flyTo(e.latlng, 8);
				setMapCenter({position: [e.latlng.lat, e.latlng.lng], zoom: 8});
				map.off("locationfound");
			},
			locationerror() {
				setInitialLoad(true);
				map.off("locationerror");
				const cityNearby = prompt("We couldn't locate you. Please enter a city nearby:");
				if (cityNearby) {
					axios.create({
						headers: {
							"Referer": "https://nominatim.openstreetmap.org/ui/search.html?q="+encodeURIComponent(cityNearby)
						}
					}).get(`https://nominatim.openstreetmap.org/search?q=${encodeURIComponent(cityNearby)}&format=json&limit=1`)
						.then(response => {
							if (response.data.length > 0) {
								const result = response.data[0];
								map.flyTo([result.lat, result.lon], 10);
								setMapCenter({position: [result.lat, result.lon], zoom: 10});
							}
						})
						.catch(() => {
							// ignore
						});
				}
			}
		});

		useEffect(() => {
			if(!initialLoad) {
				map.locate();
			}
		});

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
		<div>
			<div className={"pos-relative"}>
				<MapContainer className={"map-size-100"} center={mapCenter.position} zoom={mapCenter.zoom}
							  zoomControl={false}>
					<TileLayer
						attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
						url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
						keepBuffer={8}
					/>
					<InitialLoad/>
					<PositionUpdateEvent/>
				</MapContainer>
				<div className={"floating-box"}>
					<p>Lat: {mapCenter.position[0].toFixed(4)}, Lng: {mapCenter.position[1].toFixed(4)}</p>
					{addNewForageMapItemFormVisible ? (
						<div>
							<form>
								<select onChange={
									event => {setForageMapItemToAdd(
										{
											...forageMapItemToAdd,
											forageWikiItem: props.forageWikiItems.find(
												forageWikiItem => forageWikiItem.id === event.target.value
											) ?? {id: "", name: "", category: "", source: "", description: "", season: "", imageURLs: []}
										}
									)}
								}>
									<option defaultValue={""} selected disabled={true}>Please choose a forage item.</option>
									{
										props.forageWikiItems.map((forageWikiItem) => {
											return <option key={forageWikiItem.id} value={forageWikiItem.id}>{forageWikiItem.name}</option>
										})
									}
								</select>
								<select onChange={
									event => {setForageMapItemToAdd(
										{
											...forageMapItemToAdd,
											customMarker: props.customMarker.find(
												customMarker => customMarker.id === event.target.value
											) ?? {id: "", name: "", iconUrl: "", iconSize: [], iconAnchor: [], popupAnchor: [], popupText: ""}
										}
									)}
								}>
									<option defaultValue={""} selected disabled={true}>Please choose a marker.</option>
									{
										props.customMarker.map((customMarker) => {
											return <option key={customMarker.id} value={customMarker.id}>{customMarker.name}</option>
										})
									}
								</select>
								<span>
									<input
										type={"number"}
										defaultValue={mapCenter.position[0]}
										onChange={event => setForageMapItemToAdd(
											{
												...forageMapItemToAdd,
												position: [Number(event.target.value), forageMapItemToAdd.position[1]]
											}
										)
										}/>
									<input
										type={"number"}
										defaultValue={mapCenter.position[1]}
										onChange={event => setForageMapItemToAdd(
											{
												...forageMapItemToAdd,
												position: [forageMapItemToAdd.position[0], Number(event.target.value)]
											}
										)
										}/>
								</span>
								<select onChange={event => setForageMapItemToAdd(
									{...forageMapItemToAdd, quantity: event.target.value}
								)}>
									<option defaultValue={""} disabled={true}>Please choose a quantity.</option>
									<option value={"NONE"}>None</option>
									<option value={"LOW"}>Low</option>
									<option value={"MEDIUM"}>Medium</option>
									<option value={"HIGH"}>High</option>
									<option value={"ABUNDANT"}>Abundant</option>
								</select>
								<select onChange={event => setForageMapItemToAdd(
									{...forageMapItemToAdd, quality: event.target.value}
								)}>
									<option defaultValue={""} disabled={true}>Please choose a quality.</option>
									<option value={"POOR"}>Poor</option>
									<option value={"FAIR"}>Fair</option>
									<option value={"GOOD"}>Good</option>
									<option value={"EXCELLENT"}>Excellent</option>
								</select>
								<input type={"text"} placeholder={"Notes"} onChange={
									event => setForageMapItemToAdd(
										{...forageMapItemToAdd, notes: event.target.value}
									)
								}/>
								<p>
									<button type={"button"} onClick={submitNewForageMapItem}>Submit</button>
								</p>
							</form>
						</div>
					) : (
						<button
							onClick={() => {
								setAddNewForageMapItemFormVisible(true);
							}}
						>
							+
						</button>
					)}
				</div>
			</div>
		</div>
	)
}