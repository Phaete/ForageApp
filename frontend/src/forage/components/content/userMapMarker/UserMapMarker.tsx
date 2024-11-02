import {UserMapMarkerProps} from "./UserMapMarkerProps.ts"
import {Marker} from "react-leaflet";
import {useEffect, useState} from "react";
import L from "leaflet";

const UserMapMarker = (props: UserMapMarkerProps) => {

	const [userIcon, setUserIcon] = useState<L.Icon>(
		L.icon({
			iconUrl: "https://res.cloudinary.com/dpahk173t/image/upload/v1730207562/JamIcons_pin_alt.svg_gyqze5.png",
			iconSize: [60, 60],
			iconAnchor: [30, 60],
			popupAnchor: [0, 0]
		})
	)

	useEffect(() => {
		setUserIcon(
			L.icon({
				iconUrl: "https://res.cloudinary.com/dpahk173t/image/upload/v1730207562/JamIcons_pin_alt.svg_gyqze5.png",
				iconSize: [
					Math.min(60+((props.zoom-18)*3), 48),
					Math.min(60+((props.zoom-18)*3), 48)
				],
				iconAnchor: [
					Math.min(30+(props.zoom-18)*1.5, 24),
					Math.min(60+((props.zoom-18)*3), 48)
				]
			})
		)
	}, [props.zoom]);

	return (
		<Marker position={[props.position.latitude, props.position.longitude]} icon={userIcon}>
		</Marker>
	)
}

export default UserMapMarker