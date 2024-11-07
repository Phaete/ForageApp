import 'leaflet/dist/leaflet.css'
import {MapContainer, TileLayer, useMapEvents} from 'react-leaflet'
import {useRef, useState} from "react";
import {MapViewProps} from "./MapViewProps.ts";
import CustomMarkerMapComponent from "../../components/content/customMarkerMapComponent/CustomMarkerMapComponent.tsx";
import UserMapMarker from "../../components/content/userMapMarker/UserMapMarker.tsx";
import {GeoPosition} from "../../types/GeoPosition.ts";
import TrackingRequestMapEvent from "../../components/content/trackingRequestMapEvent/TrackingRequestMapEvent.tsx";
import TemporaryForageMapMarker from "../../components/content/temporaryForageMapMarker/TemporaryForageMapMarker.tsx";
import {ForageMapItem} from "../../types/ForageMapItem.ts";
import FloatingMapInfoBox from "../../components/content/floatingMapInfoBox/FloatingMapInfoBox.tsx";
import FloatingMapTrackerButton from "../../components/content/floatingMapTrackerButton/FloatingMapTrackerButton.tsx";
import DetailedForageMapItemCard
	from "../../components/content/detailedForageMapItemCard/DetailedForageMapItemCard.tsx";

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

	const [detailedForageMapItem, setDetailedForageMapItem] = useState<ForageMapItem | null>(null)

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
			<div className={"pos-relative flex flex-col justify-center align-center"}>
					<MapContainer className={`${detailedForageMapItem === null ? 'map-size-100' : 'map-size-50'} boxed-r10`}
							  center={[mapCenter.position.latitude, mapCenter.position.longitude]}
							  zoom={mapCenter.zoom}
							  zoomControl={false}
							  attributionControl={false}
							  ref={mapRef}>
						<TileLayer
							url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
							keepBuffer={24}
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
										zoom={mapCenter.zoom}
										setDetailedForageMapItem={setDetailedForageMapItem}
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
								setAddForageMapItem={setAddForageMapItem}
								setDetailedForageMapItem={setDetailedForageMapItem}/>
						}
					</MapContainer>
				{detailedForageMapItem === null ?
					<div>
						<FloatingMapInfoBox
							mapCenter={mapCenter}
							userPosition={userPosition}
							setAddForageMapItem={setAddForageMapItem}/>
						<FloatingMapTrackerButton
							trackingAllowed={trackingAllowed}
							requestTracking={requestTracking}
							handleTrackingToggle={handleTrackingToggle}
							setRequestTracking={setRequestTracking}/>
					</div>
					:
					<div className={"flex justify-center align-center"}>
						<DetailedForageMapItemCard
							forageWikiItems={props.forageWikiItems}
							customMarker={props.customMarker}
							forageMapItem={detailedForageMapItem}
							fetchForageMapItems={props.fetchForageMapItems}
							setDetailedForageMapItem={setDetailedForageMapItem}/>
					</div>
				}
			</div>
		</div>
	)
}