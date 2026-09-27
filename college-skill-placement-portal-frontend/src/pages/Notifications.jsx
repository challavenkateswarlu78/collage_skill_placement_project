import { useEffect, useState } from "react";
import { useAuth } from "../context/AuthContext";
import api from "../services/api";
import Navbar from "../components/Navbar";

function Notifications() {
    const { userId } = useAuth();

    const [notifications, setNotifications] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");

    useEffect(() => {
        const fetchNotifications = async () => {
            if (!userId) {
                setError("User ID not found.");
                setLoading(false);
                return;
            }

            try {
                const response = await api.get(
                    `/api/notifications/user/${userId}`
                );

                console.log(
                    "Notifications:",
                    response.data
                );

                setNotifications(response.data);
            } catch (err) {
                console.error(
                    "Notifications fetch error:",
                    err
                );

                setError(
                    err.response?.data?.message ||
                    "Failed to load notifications."
                );
            } finally {
                setLoading(false);
            }
        };

        fetchNotifications();
    }, [userId]);

    const markAllAsRead = async () => {
    try {
        await api.put(
            `/api/notifications/user/${userId}/read-all`
        );

        setNotifications((current) =>
            current.map((notification) => ({
                ...notification,
                read: true,
            }))
        );
    } catch (err) {
        console.error(
            "Mark all notifications as read error:",
            err
        );

        setError(
            err.response?.data?.message ||
            "Failed to mark all notifications as read."
        );
    }
};
    const markAsRead = async (notificationId) => {
        try {
            const response = await api.put(
                `/api/notifications/${notificationId}/read/${userId}`
            );

            setNotifications((current) =>
                current.map((notification) =>
                    notification.id === notificationId
                        ? response.data
                        : notification
                )
            );
        } catch (err) {
            console.error(
                "Mark notification as read error:",
                err
            );
        }
    };

    if (loading) {
        return (
            <h2>
                Loading notifications...
            </h2>
        );
    }

    return (
        <div>
            <h1>Notifications</h1>

            <Navbar/>

            {notifications.some(
    (notification) => !notification.read
) && (
    <div className="notification-actions">
        <button
            className="mark-all-button"
            onClick={markAllAsRead}
        >
            Mark All as Read
        </button>
    </div>
)}

            {error && (
    <p className="error-message">
        {error}
    </p>
)}

            {!error &&
    notifications.length === 0 && (
        <div className="empty-notifications">
            <h2>No Notifications</h2>
            <p>
                You have no notifications at the moment.
            </p>
        </div>
    )}

            <div className="notifications-list">
    {notifications.map((notification) => (
        <div
            className={`notification-card ${
                notification.read
                    ? "notification-read"
                    : "notification-unread"
            }`}
            key={notification.id}
        >
            <div className="notification-header">

                <h2>
                    {notification.title}
                </h2>

                <span
                    className={`notification-status ${
                        notification.read
                            ? "status-read"
                            : "status-unread"
                    }`}
                >
                    {notification.read
                        ? "Read"
                        : "Unread"}
                </span>

            </div>

            <p className="notification-message">
                {notification.message}
            </p>

            <div className="notification-meta">

                <p>
                    <span>Type</span>
                    <strong>
                        {notification.type}
                    </strong>
                </p>

                <p>
                    <span>Date</span>
                    <strong>
                        {notification.createdAt
                            ? new Date(
                                  notification.createdAt
                              ).toLocaleString()
                            : "N/A"}
                    </strong>
                </p>

            </div>

            {!notification.read && (
                <button
                    className="mark-read-button"
                    onClick={() =>
                        markAsRead(
                            notification.id
                        )
                    }
                >
                    Mark as Read
                </button>
            )}
        </div>
    ))}
</div>
        </div>
    );
}

export default Notifications;