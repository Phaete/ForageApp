import {TrackingRequestMapEventProps} from "./TrackingRequestMapEventProps.ts"
import {useMapEvents} from "react-leaflet";
import axios from "axios";
import {useEffect, useState} from "react";

const TrackingRequestMapEvent = (props: TrackingRequestMapEventProps) => {

	const [initialLoad, setInitialLoad] = useState<boolean>(false)

	const map = useMapEvents({
		locationfound(e) {
			if (props.trackingAllowed) {
				props.setUserPosition({
					latitude: +e.latlng.lat,
					longitude: +e.latlng.lng
				})
			}
			if (!initialLoad) {
				setInitialLoad(true);
				props.setTrackingAllowed(true);
				map.flyTo(e.latlng, 8);
				props.setMapCenter({
					position: {
						latitude: +e.latlng.lat,
						longitude: +e.latlng.lng
					}, zoom: 8});

			}
			//map.off("locationfound");
		},
		locationerror() {
			console.log(props.mapRef)
			props.setTrackingAllowed(false);
			props.setRequestTracking(false);
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
							props.setMapCenter({
								position: {
									latitude: +result.lat,
									longitude: +result.lon
								}, zoom: 10});
							props.setUserPosition({
								latitude: +result.lat,
								longitude: +result.lon
							})
						}
					})
					.catch(() => {
						// ignore
					});
			}
		}
	});

	function requestToLocateMe() {
		if (props.mapRef.current) {
			props.mapRef.current.locate();
		}
	}

	useEffect(() => {
		if (props.requestTracking){
			requestToLocateMe()
		}
	}, [props.requestTracking]);

	return null;
}

export default TrackingRequestMapEvent;