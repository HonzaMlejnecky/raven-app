import '../../index-out.css'


function Card(props: any) {

    return (
        <div className="bg-white rounded-md border border-gray-100 p-6 shadow-md shadow-black/5">
            <div className="flex justify-between mb-12 w-32">
                <div>
                    <div className="flex items-center mb-8">
                        <div className="text-2xl font-semibold">{props.value}</div>
                    </div>
                    <div className="text-sm font-medium text-gray-400">{props.title}</div>
                </div>
            </div>
        </div>
    )
}

export default Card
