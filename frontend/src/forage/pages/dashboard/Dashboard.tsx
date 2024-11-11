import {DashboardProps} from "./DashboardProps.ts"

export default function Dashboard(props: Readonly<DashboardProps>) {

	return (
		<>
			<img src={props.user?.imageUrl} alt={"profile-picture"} width={"200px"}/>
            <p>{props.user?.name}</p>
		</>
	)
}