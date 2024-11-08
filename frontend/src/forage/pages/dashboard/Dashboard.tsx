import {DashboardProps} from "./DashboardProps.ts"
import {useEffect} from "react";

export default function Dashboard(props: Readonly<DashboardProps>) {

	useEffect(() => {
		if (props.user == null) {
			props.getMe()
		}
	});

	return (
		<>
			<img src={props.user?.imageUrl} alt={"profile-picture"} width={"200px"}/>
            <p>{props.user?.name}</p>
		</>
	)
}