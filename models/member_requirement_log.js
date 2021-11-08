/* jshint indent: 2 */

module.exports = function(sequelize, DataTypes) {
  return sequelize.define('member_requirement_log', {
    'id': {
      type: DataTypes.INTEGER,
      allowNull: false,
      primaryKey: true,
      primaryKey: true,
      comment: "null",
      autoIncrement: true
    },
    'memberRequirementId': {
      type: DataTypes.INTEGER,
      allowNull: false,
      comment: "null"
    },
    'memberId': {
      type: DataTypes.INTEGER,
      allowNull: false,
      comment: "null"
    },
    'requirementId': {
      type: DataTypes.INTEGER,
      allowNull: false,
      comment: "null"
    },
    'estimateStartDate': {
      type: DataTypes.DATE,
      allowNull: false,
      comment: "null"
    },
    'estimateEndDate': {
      type: DataTypes.DATE,
      allowNull: false,
      comment: "null"
    },
    'estimateHours': {
      type: "DOUBLE",
      allowNull: false,
      comment: "null"
    },
    'realHours': {
      type: "DOUBLE",
      allowNull: true,
      comment: "null"
    },
    'comment': {
      type: DataTypes.STRING(255),
      allowNull: true,
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
    tableName: 'member_requirement_log',
    paranoid: true
  });
};
