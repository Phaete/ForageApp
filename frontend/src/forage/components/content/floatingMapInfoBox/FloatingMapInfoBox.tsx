import {FloatingMapInfoBoxProps} from "./FloatingMapInfoBoxProps.ts"
import {Button, Card} from "react-bootstrap";

export default function FloatingMapInfoBox(props: Readonly<FloatingMapInfoBoxProps>) {
	return (
        <Card className={"floating-box bg-white top-5 left-5 text-black font-small"}>
            <Card.Header>
                {props.userPosition ? <div className={"flex flex-col flex-start"}>
                    User Position: <span>
                        Lat: {props.userPosition?.latitude.toFixed(4)},
                        Lng: {props.userPosition?.longitude.toFixed(4)}
                    </span>
                </div>
                :
                <div className={"flex flex-col flex-start font-small"}>
                    <span>Map Center: </span>
                    <span>
                        Lat: {props.mapCenter.position.latitude.toFixed(4)},
                        Lng: {props.mapCenter.position.longitude.toFixed(4)}
                    </span>
                </div>
                }
            </Card.Header>
            <Card.Body>
                <Button className={"m-0 mpx-5 p-0 ppx-5"} type={"button"} variant={"secondary"} onClick={() => {
                    props.setAddForageMapItem(true)
                }}><div className={"font-small"}>Add forage item at current position</div>
                </Button>
            </Card.Body>
        </Card>
    )
}