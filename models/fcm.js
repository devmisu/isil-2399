/* jshint indent: 2 */

module.exports = function(sequelize, DataTypes) {
  return sequelize.define('fcm', {
    'id': {
      type: DataTypes.INTEGER(11),
      allowNull: false,
      primaryKey: true,
      primaryKey: true,
      comment: "null",
      autoIncrement: true
    },
    'memberId': {
      type: DataTypes.INTEGER(11),
      allowNull: false,
      comment: "null"
    },
    'fcmToken': {
      type: DataTypes.STRING(255),
      allowNull: false,
      comment: "null"
    },
    'createdAt': {
      type: DataTypes.DATE,
      allowNull: false,
      comment: "null"
    },
    'updatedAt': {
      type: DataTypes.DATE,
      allowNull: true,
      comment: "null"
    },
    'deletedAt': {
      type: DataTypes.DATE,
      allowNull: true,
      comment: "null"
    }
  }, {
    tableName: 'fcm',
    paranoid: true
  });
};
