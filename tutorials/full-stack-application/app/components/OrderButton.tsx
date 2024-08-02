'use client'
 
import { useFormStatus } from 'react-dom'
 
const OrderButton = () => {
  const { pending } = useFormStatus()
 
  return (
    <button className="order_button" type="submit" disabled={pending}>
      {pending ? `Ordering...`: "Order?"}
    </button>
  )
}

export default OrderButton;
