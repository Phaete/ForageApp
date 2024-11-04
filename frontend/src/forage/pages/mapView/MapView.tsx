import 'leaflet/dist/leaflet.css'
import {MapContainer, TileLayer, useMapEvents} from 'react-leaflet'
import {useRef, useState} from "react";
import {MapViewProps} from "./MapViewProps.ts";
import CustomMarkerMapComponent from "../../components/content/customMarkerMapComponent/CustomMarkerMapComponent.tsx";
import UserMapMarker from "../../components/content/userMapMarker/UserMapMarker.tsx";
import {GeoPosition} from "../../types/GeoPosition.ts";
import TrackingRequestMapEvent from "../../components/content/trackingRequestMapEvent/TrackingRequestMapEvent.tsx";
import TemporaryForageMapMarker from "../../components/content/temporaryForageMapMarker/TemporaryForageMapMarker.tsx";

export default function MapView(props: Readonly<MapViewProps>) {

	const mapRef = useRef(null)
	const [mapCenter, setMapCenter] = useState<{ position: GeoPosition, zoom: number }>(
		{
			position: {
				latitude: 51.0,
				longitude: 10.0
			},
			zoom: 6
		}
	)
	const [userPosition, setUserPosition] = useState<GeoPosition | null>(null)

	const [requestTracking, setRequestTracking] = useState<boolean>(false)
	const [trackingAllowed, setTrackingAllowed] = useState<boolean>(false)

	const [addForageMapItem, setAddForageMapItem] = useState<boolean>(false)

	const MapMoveEvent = () => {
		useMapEvents({
			move: (e) => {
				setMapCenter({
					position: {
						latitude: e.target.getCenter().lat,
						longitude: e.target.getCenter().lng
					}, zoom: e.target.getZoom()})
			}
		})
		return null;
	}

	function handleTrackingToggle() {
		if (requestTracking) {
			setTrackingAllowed(!trackingAllowed)
		}
	}

	return (
		<div>
			<div className={"pos-relative"}>
				<MapContainer className={"map-size-100"}
							  center={[mapCenter.position.latitude, mapCenter.position.longitude]}
							  zoom={mapCenter.zoom}
							  zoomControl={false}
							  attributionControl={false}
							  ref={mapRef}>
					<TileLayer
						url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
						keepBuffer={8}
					/>
					{requestTracking && <TrackingRequestMapEvent
						mapRef={mapRef}
						mapCenter={mapCenter}
						setMapCenter={setMapCenter}
						trackingAllowed={trackingAllowed}
						setTrackingAllowed={setTrackingAllowed}
						setUserPosition={setUserPosition}
						requestTracking={requestTracking}
						setRequestTracking={setRequestTracking}/>}
					<MapMoveEvent/>
					{
						props.forageMapItems && props.forageMapItems.length > 0 && props.forageMapItems.map((forageMapItem) => (
								<CustomMarkerMapComponent
									key={forageMapItem.id}
								  	forageMapItem={forageMapItem}
									fetchForageMapItems={props.fetchForageMapItems}
									forageWikiItems={props.forageWikiItems}
									customMarker={props.customMarker}
									zoom={mapCenter.zoom}
								/>
							)
						)
					}
					{userPosition && <UserMapMarker userPosition={userPosition} setUserPosition={setUserPosition} zoom={mapCenter.zoom}/>}
					{addForageMapItem &&
						<TemporaryForageMapMarker
							position={mapCenter.position}
							fetchForageMapItems={props.fetchForageMapItems}
							forageWikiItems={props.forageWikiItems}
							customMarker={props.customMarker}
							setAddForageMapItem={setAddForageMapItem}/>
					}
				</MapContainer>
				<div className={"floating-box boxed-r5 bg-white p-5 top-5 left-5"}>
					{userPosition ? <p>
						User Position: <span>
							Lat: {userPosition?.latitude.toFixed(4)}, Lng: {userPosition?.longitude.toFixed(4)}
						</span>
					</p>
					:
						<div className={"flex flex-col flex-start"}>
							<span>Map Center: </span>
							<span>Lat: {mapCenter.position.latitude.toFixed(4)}, Lng: {mapCenter.position.longitude.toFixed(4)}</span>
						</div>}
					<button type={"button"} onClick={() => {
						setAddForageMapItem(true)
					}}>Add forage item at current position</button>
				</div>
				<div className={"floating-box bottom-5 right-5"}>
					{requestTracking ?
						<button type={"button"} onClick={() => handleTrackingToggle()}
							 className={`flex align-center justify-center no-text-deco button ${trackingAllowed ? 'tracking-allowed' : 'tracking-denied'} bg-white`}>
						</button>
					:
						<button type={"button"} onClick={() => setRequestTracking(true)}
								className={"flex align-center justify-center no-text-deco button locate-me bg-white"}>
						</button>
					}
				</div>
			</div>
		</div>
	)
}