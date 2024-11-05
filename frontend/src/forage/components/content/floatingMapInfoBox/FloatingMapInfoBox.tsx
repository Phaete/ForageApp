import {FloatingMapInfoBoxProps} from "./FloatingMapInfoBoxProps.ts"

export default function FloatingMapInfoBox(props: Readonly<FloatingMapInfoBoxProps>) {
	return (
        <div className={"floating-box boxed-r5 bg-white p-5 top-5 left-5"}>
            {props.userPosition ? <p>
                    User Position: <span>
									Lat: {props.userPosition?.latitude.toFixed(4)}, Lng: {props.userPosition?.longitude.toFixed(4)}
								</span>
                </p>
                :
                <div className={"flex flex-col flex-start"}>
                    <span>Map Center: </span>
                    <span>Lat: {props.mapCenter.position.latitude.toFixed(4)}, Lng: {props.mapCenter.position.longitude.toFixed(4)}</span>
                </div>}
            <button type={"button"} onClick={() => {
                props.setAddForageMapItem(true)
            }}>Add forage item at current position
            </button>
        </div>
    )
}